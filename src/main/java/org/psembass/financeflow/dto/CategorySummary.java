package org.psembass.financeflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.psembass.financeflow.enums.CategoryType;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategorySummary implements Serializable {
    private Long categoryId;
    private String categoryName;
    private CategoryType type;
    private BigDecimal total;
}
