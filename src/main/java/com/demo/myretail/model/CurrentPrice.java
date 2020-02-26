package com.demo.myretail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
public class CurrentPrice {

    @NotNull (message = "Should not be Null")
    @Positive (message = "Price should be positive")
    private Double value;

    @Pattern(regexp = "USD",message="Only USD is acceptable")
    @JsonProperty("currency_code")
    private String currencyCode;
}
