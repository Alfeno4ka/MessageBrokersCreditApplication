package com.example.repository;

import com.example.entity.CreditApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий работы с сущностями кредитных заявок {@link CreditApplicationEntity}.
 */
@Repository
public interface CreditApplicationRepository extends JpaRepository<CreditApplicationEntity, UUID> {
}
