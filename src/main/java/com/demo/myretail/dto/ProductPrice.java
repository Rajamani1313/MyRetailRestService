package com.demo.myretail.dto;

import lombok.Data;

/**
 * Domain class for entity
 */
@Data
public class ProductPrice {

    private Long productId;
    private Double price;
    private String currency;

}
