package com.dd.merchant.model.ui;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


import java.util.List;

/**
 * Model class for API response
 */
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UIResponse {

    /**
     * List of Response objects
     */
    @JsonProperty("results")
    private List<Response> results;

    /**
     * error populated only for exception
     */
    @JsonProperty("error")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;
}
