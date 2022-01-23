package com.dd.merchant.model.ui;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Model class for UI request
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UIRequest {

    /**
     * rawPhoneNumbers
     */
    @JsonProperty("raw_phone_numbers")
    private String rawPhoneNumbers;

}
