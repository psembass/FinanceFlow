package org.psembass.financeflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
}
