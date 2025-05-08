package com.example.mapper;

import com.example.dto.frontal.CreditApplicationRequest;
import com.example.dto.integration.CreditApplicationOutboundDto;
import com.example.entity.CreditApplicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Маппер моделей данных.
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CreditApplicationMapper {

    /**
     * Преобразовать объект запроса на создание кредитной заявки в доменную сущность.
     *
     * @param from объект запроса {@link CreditApplicationRequest}
     * @return результат - доменная сущность {@link CreditApplicationEntity}
     */
    CreditApplicationEntity toCreditApplicationEntity(CreditApplicationRequest from);

    /**
     * Преобразовать сущность кредитной завки в модель данных для брокера сообщений.
     *
     * @param from сущность {@link CreditApplicationEntity}
     * @return результат в виде {@link CreditApplicationOutboundDto}
     */
    CreditApplicationOutboundDto toCreditApplicationBrokerDto(CreditApplicationEntity from);
}
