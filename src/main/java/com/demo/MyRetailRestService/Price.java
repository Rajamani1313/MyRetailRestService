package com.demo.MyRetailRestService;

import lombok.Data;

@Data
public class Price {
    private Long productId;
    private Double price;
    private String currency;
}
