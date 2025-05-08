package com.example.controller;

import com.example.dto.frontal.CreditApplicationCreatedResponse;
import com.example.dto.frontal.CreditApplicationRequest;
import com.example.dto.frontal.CreditApplicationStatusResponse;
import com.example.exception.InvalidUUIDException;
import com.example.service.CreditApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Контроллер работы с заявками на кредит.
 */
@Validated
@RestController
@RequiredArgsConstructor
public class CreditApplicationController {

    private final CreditApplicationService creditApplicationService;

    @PostMapping(value = "/api/credit-application",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreditApplicationCreatedResponse> addCreditApplication(
            @RequestBody @Valid CreditApplicationRequest request) {
        return ResponseEntity.ok(creditApplicationService.addCreditApplication(request));
    }

    @GetMapping(value = "/api/credit-application/{applicationId}/status",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreditApplicationStatusResponse> getCreditApplicationStatus(@PathVariable String applicationId) {
        UUID applicationIdUUID;
        try {
            applicationIdUUID = UUID.fromString(applicationId);
        } catch (IllegalArgumentException e) {
            throw new InvalidUUIDException(applicationId);
        }

        return ResponseEntity.ok(creditApplicationService.getCreditApplicationStatus(applicationIdUUID));
    }
}
