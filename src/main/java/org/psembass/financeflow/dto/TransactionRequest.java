package org.psembass.financeflow.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionRequest {
    @NotNull
    @Positive
    private BigDecimal amount;
    private String description;
    @NotNull
    private LocalDate date;
    @NotNull
    private Long categoryId;
}
