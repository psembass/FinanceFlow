package org.psembass.financeflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class SummaryResponse {
    private LocalDate from;
    private LocalDate to;
    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal balance;
    private List<CategorySummary> byCategory;
}
