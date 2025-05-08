package com.example.entity;

/**
 * Статусная модель кредитной заявки.
 */
public enum ApplicationStatus {

    /**
     * Заявка на рассмотрении (создана).
     */
    PENDING,

    /**
     * Заявка одобрена.
     */
    APPROVED,

    /**
     * Заявка отклонена
     */
    DECLINED
}
