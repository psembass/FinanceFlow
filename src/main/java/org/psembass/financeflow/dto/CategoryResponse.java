package org.psembass.financeflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.psembass.financeflow.enums.CategoryType;

@AllArgsConstructor
@Getter
public class CategoryResponse {
    private Long id;
    private String name;
    private CategoryType type;
}
