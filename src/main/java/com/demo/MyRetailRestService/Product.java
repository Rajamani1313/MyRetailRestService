package com.demo.MyRetailRestService;

import lombok.Data;

@Data
public class Product {
    private Long productId;
    private String name;
    private CurrentPrice current_price;
}
