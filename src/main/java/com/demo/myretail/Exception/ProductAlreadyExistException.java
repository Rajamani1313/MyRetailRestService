package com.demo.myretail.Exception;

public class ProductAlreadyExistException extends RuntimeException {
    public ProductAlreadyExistException(String message){
        super(message);
    }
}
