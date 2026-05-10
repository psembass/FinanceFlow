package org.psembass.financeflow.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum CategoryType {
    INCOME, EXPENSE;

    @JsonCreator
    public static CategoryType fromValue(String value) {
        for (CategoryType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid value '" + value + "'. Accepted values: " + Arrays.toString(values()));
    }
}
