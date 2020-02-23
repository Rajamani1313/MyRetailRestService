package com.demo.MyRetailRestService;

import lombok.Data;

@Data
public class CurrentPrice {
    private Double value;
    private String currency_code;
}
