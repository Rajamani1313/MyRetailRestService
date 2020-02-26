package com.demo.myretail.dto;

import lombok.Data;

@Data
public class ProductPrice {

    private Long productId;
    private Double price;
    private String currency;
}
