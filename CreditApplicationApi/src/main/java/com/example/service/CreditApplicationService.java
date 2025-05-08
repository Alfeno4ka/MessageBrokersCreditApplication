package com.example.service;

import com.example.constants.InfoLogMessages;
import com.example.dto.frontal.CreditApplicationCreatedResponse;
import com.example.dto.frontal.CreditApplicationRequest;
import com.example.dto.frontal.CreditApplicationStatusResponse;
import com.example.dto.integration.CreditApplicationOutboundDto;
import com.example.dto.integration.CreditApplicationSolutionInboundDto;
import com.example.entity.ApplicationStatus;
import com.example.entity.CreditApplicationEntity;
import com.example.exception.CreditApplicationNotFoundException;
import com.example.mapper.CreditApplicationMapper;
import com.example.repository.CreditApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Сервис управления кредитными заявками.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CreditApplicationService {
    private final CreditApplicationRepository creditApplicationRepository;
    private final CreditApplicationMapper creditApplicationMapper;
    private final KafkaTemplate<String, CreditApplicationOutboundDto> kafkaTemplate;

    private static final String CREDIT_SOLUTION_INBOUND_QUEUE = "${rabbitmq.outbound-queue}";

    @Value("${spring.kafka.template.default-topic}")
    private String creditApplicationOutboundTopic;


    /**
     * Создать заявку на кредит.
     *
     * @param request ДТО заявки на кредит
     * @return результат в виде {@link CreditApplicationCreatedResponse}
     */
    @Transactional
    public CreditApplicationCreatedResponse addCreditApplication(CreditApplicationRequest request) {
        log.info(InfoLogMessages.ADDING_NEW_CREDIT_APPLICATION, request);

        CreditApplicationEntity toSave = creditApplicationMapper.toCreditApplicationEntity(request);
        toSave.setStatus(ApplicationStatus.PENDING);
        CreditApplicationEntity saved = creditApplicationRepository.save(toSave);
        log.info(InfoLogMessages.CREDIT_APPLICATION_SAVED, saved.getId());

        CreditApplicationOutboundDto toSend = creditApplicationMapper.toCreditApplicationBrokerDto(saved);
        kafkaTemplate.send(creditApplicationOutboundTopic, String.valueOf(toSend.getId()), toSend);
        log.info(InfoLogMessages.CREDIT_APPLICATION_SENT_TO_PROCESSING_SERVICE, toSend);

        return CreditApplicationCreatedResponse.builder()
                .applicationId(saved.getId())
                .build();
    }

    /**
     * Получить статус креитной заявки.
     *
     * @param applicationId идентификатор кредитной заявки
     * @return результат в виде ДТО {@link CreditApplicationStatusResponse}
     */
    @Transactional(readOnly = true)
    public CreditApplicationStatusResponse getCreditApplicationStatus(UUID applicationId) {
        CreditApplicationEntity found = creditApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new CreditApplicationNotFoundException(applicationId));

        return CreditApplicationStatusResponse.builder()
                .applicationId(found.getId())
                .status(found.getStatus())
                .build();
    }

    /**
     * Обработать решение по кредитной заявке.
     *
     * @param solutionDto модель решения по кредитной заявке из сервиса обработки кредитных заявок.
     */
    @RabbitListener(queues = CREDIT_SOLUTION_INBOUND_QUEUE)
    @Transactional
    public void receiveMessage(CreditApplicationSolutionInboundDto solutionDto) {
        log.info(InfoLogMessages.CREDIT_SOLUTION_RECEIVED, solutionDto);
        UUID applicationId = solutionDto.getApplicationId();

        CreditApplicationEntity existing = creditApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new CreditApplicationNotFoundException(applicationId));

        existing.setStatus(solutionDto.getApplicationSolution());

        creditApplicationRepository.save(existing);
        log.info(InfoLogMessages.CREDIT_APPLICATION_UPDATED, existing.getId(), solutionDto.getApplicationSolution());
    }
}
