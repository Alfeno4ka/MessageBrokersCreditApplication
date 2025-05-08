create schema if not exists message_brokers_credit_system;

create table if not exists message_brokers_credit_system.dat_credit_application
(
    id uuid not null primary key,
    credit_amount numeric(8, 2) not null,
    borrowers_monthly_income numeric(8, 2) not null,
    borrowers_credit_load numeric(5, 2) not null,
    borrowers_credit_rate numeric(5, 2) not null,
    credit_term_months integer not null,
    status varchar not null
);