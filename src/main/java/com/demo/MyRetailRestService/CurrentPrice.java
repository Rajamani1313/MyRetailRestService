package com.demo.MyRetailRestService;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
public class CurrentPrice {

    @Valid
    @NotNull
    @Positive
    private Double value;

    @Valid
    @Pattern(regexp = "USD",message="Only USD is acceptable")
    private String currency_code;
}
