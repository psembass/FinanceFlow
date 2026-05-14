package org.psembass.financeflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.psembass.financeflow.enums.CategoryType;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CategorySummary {
    private Long categoryId;
    private String categoryName;
    private CategoryType type;
    private BigDecimal total;
}
