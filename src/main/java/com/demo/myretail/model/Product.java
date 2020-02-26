package com.demo.myretail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Model class for Product
 */
@Data
public class Product {
    @NotNull (message = "Should not be Null")
    @Min(value = 10000000, message = "Value should be greater than 10000000")
    @Max(value = 99999999 , message = "Value should be less than 99999999")
    private Long id;

    @NotNull (message = "Should not be Null")
    private String name;

    @NotNull (message = "Should not be Null")
    @JsonProperty("current_price")
    private CurrentPrice currentPrice;
}
