package com.demo.MyRetailRestService;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Model class for Product
 */
@Data
public class Product {
    @Id
    @Valid
    @NotNull
    @Min(10000000)
    @Max(99999999)
    private Long id;

    @Valid
    @NotNull
    private String name;


    @Valid
    @NotNull
    private CurrentPrice current_price;
}
