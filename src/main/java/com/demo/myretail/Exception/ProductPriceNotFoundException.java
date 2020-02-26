package com.demo.myretail.Exception;

public class ProductPriceNotFoundException extends RuntimeException {
    public ProductPriceNotFoundException(String message){
        super(message);
    }
}
