package com.demo.myretail.Exception;

public class ProductDescriptionNotFoundException extends RuntimeException {
    public ProductDescriptionNotFoundException(String message){
        super(message);
    }
}
