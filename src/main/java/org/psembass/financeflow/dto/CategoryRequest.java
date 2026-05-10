package org.psembass.financeflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.psembass.financeflow.enums.CategoryType;

@Getter
public class CategoryRequest {
    @NotBlank
    private String name;
    @NotNull
    private CategoryType type;
}
