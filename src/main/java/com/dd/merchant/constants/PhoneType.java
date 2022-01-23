package com.dd.merchant.constants;

import lombok.Getter;
import lombok.Setter;

/**
 * PhoneType for phoneNumber
 */
public enum PhoneType {

    /**
     * Home type
     */
    HOME("home"),
    /**
     * Cell type
     */
    CELL("cell");

    @Getter
    @Setter
    private String value;

    /**
     * Constructor for PhoneType
     * @param value
     */
    PhoneType(String value) {
        this.value = value;
    }
}
