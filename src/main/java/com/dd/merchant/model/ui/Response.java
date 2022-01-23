package com.dd.merchant.model.ui;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * Response object for UI
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    /**
     * id
     */
    @JsonProperty("id")
    private String id;

    /**
     * phoneNumber
     */
    @JsonProperty("phone_number")
    private String phoneNumber;

    /**
     * phoneType
     */
    @JsonProperty("phone_type")
    private String phoneType;

    /**
     * occurrences
     */
    @JsonProperty("occurrences")
    private BigInteger occurrences;

}
