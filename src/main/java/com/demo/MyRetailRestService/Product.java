package com.demo.MyRetailRestService;

import lombok.Data;

@Data
public class Product {
    private String id;
    private String name;
    private CurrentPrice current_price;
}
