package org.psembass.financeflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String email;

    public void setEmail(String email) {
        this.email = email != null ? email.trim().toLowerCase() : null;
    }
}
