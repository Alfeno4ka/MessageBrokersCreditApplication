package com.example.service;

import com.example.constants.InfoLogMessages;
import com.example.dto.integration.CreditApplicationInboundDto;
import com.example.dto.integration.CreditApplicationSolutionOutboundDto;
import com.example.dto.integration.Solution;
import com.example.util.BigDecimalUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

/**
 * Сервис обработки кредитных заявок.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CreditApplicationProcessingService {
    private static final String CREDIT_APPLICATION_TOPIC = "${kafka.inbound.topic}";
    private static final String GROUP_ID ="${spring.kafka.consumer.group-id}";

    @Value("${rabbitmq.outbound-queue}")
    private String queueName;

    private final RabbitTemplate rabbitTemplate;

    /**
     * Обработать входящую кредитную заявку и сформировать решение.
     *
     * @param creditApplication кредитная заявка.
     */
    @Transactional
    @KafkaListener(topics = CREDIT_APPLICATION_TOPIC,
            groupId = GROUP_ID,
            properties = {"spring.json.value.default.type=com.example.dto.integration.CreditApplicationInboundDto"})
    public void processCreditApplication(CreditApplicationInboundDto creditApplication) {
        log.info(InfoLogMessages.RECEIVED_CREDIT_APPLICATION, creditApplication);

        CreditApplicationSolutionOutboundDto solutionDto = calculateSolution(creditApplication);
        log.info(InfoLogMessages.CREDIT_SOLUTION_FORMED, solutionDto.getApplicationId(), solutionDto.getApplicationSolution());

        rabbitTemplate.convertAndSend(queueName, calculateSolution(creditApplication));
        log.info(InfoLogMessages.CREDIT_SOLUTION_SENT, solutionDto);
    }

    private CreditApplicationSolutionOutboundDto calculateSolution(CreditApplicationInboundDto creditApplication) {
        UUID applicationId = creditApplication.getId();
        BigDecimal currentCreditLoadPercents = creditApplication.getBorrowersCreditLoad();

        //Если кредитная нагрузка уже выше 50% - возвращаем отказ
        if (currentCreditLoadPercents.compareTo(BigDecimalUtils.FIFTY) > 0) {
            return buildSolution(applicationId, Solution.DECLINED);
        }

        BigDecimal creditAmount = creditApplication.getCreditAmount();
        Integer creditTerm = creditApplication.getCreditTermInMonths();

        //Высчитаем ежемесячный платёж
        BigDecimal monthlyIncome = creditApplication.getBorrowersMonthlyIncome();
        BigDecimal monthlyPayment = creditAmount.divide(BigDecimal.valueOf(creditTerm), 2, RoundingMode.HALF_UP);

        //Высчитаем результирующую кредитную нагрузку (прошла нагрузка + ежемесячный платёж по новому кредиту)
        BigDecimal currentAbsoluteCreditLoad = BigDecimalUtils.percentsOf(monthlyIncome, currentCreditLoadPercents);
        BigDecimal resultAbsoluteCreditLoad = currentAbsoluteCreditLoad.add(monthlyPayment);
        BigDecimal resultCreditLoadPercents = BigDecimalUtils.percentsDiff(monthlyPayment, resultAbsoluteCreditLoad);

        //Проверяем результирующую нагрузку формируем решение.
        if (resultCreditLoadPercents.compareTo(BigDecimalUtils.FIFTY) > 0) {
            return buildSolution(applicationId, Solution.DECLINED);
        }

        return buildSolution(applicationId, Solution.APPROVED);
    }

    private CreditApplicationSolutionOutboundDto buildSolution(UUID applicationId, Solution solution) {
        CreditApplicationSolutionOutboundDto solutionDto = new CreditApplicationSolutionOutboundDto();
        solutionDto.setApplicationId(applicationId);
        solutionDto.setApplicationSolution(solution);

        return solutionDto;
    }
}
