package org.psembass.financeflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Getter
public class TransactionResponse {
    private Long id;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private Long userId;
    private CategoryResponse category;
    private OffsetDateTime createdAt;
}
