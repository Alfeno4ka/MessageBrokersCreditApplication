package com.example.config;

import com.example.dto.integration.CreditApplicationOutboundDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

/**
 * Конфигурация продюсеров кафки.
 */
@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<String, CreditApplicationOutboundDto> producerFactory() {
        Map<String, Object> producerProps = kafkaProperties.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(producerProps);
    }

    @Bean
    public KafkaTemplate<String, CreditApplicationOutboundDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
