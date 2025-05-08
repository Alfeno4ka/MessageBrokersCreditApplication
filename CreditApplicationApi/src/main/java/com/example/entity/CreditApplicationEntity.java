package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Сущность "Заявка на получение кредита".
 */
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dat_credit_application", schema = "message_brokers_credit_system")
public class CreditApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "credit_amount", precision = 8, scale = 2, nullable = false)
    private BigDecimal creditAmount;

    @Column(name = "borrowers_monthly_income", precision = 8, scale = 2, nullable = false)
    private BigDecimal borrowersMonthlyIncome;

    @Column(name = "borrowers_credit_load", precision = 5, scale = 2, nullable = false)
    private BigDecimal borrowersCreditLoad;

    @Column(name = "borrowers_credit_rate", precision = 5, scale = 2, nullable = false)
    private BigDecimal borrowersCreditRate;

    @Column(name = "credit_term_months", nullable = false)
    private Integer creditTermInMonths;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
}
