package com.demo.myretail.Exception;

import com.demo.myretail.model.ProductMessage;

public class ProductIdValidationException extends RuntimeException {
    public ProductIdValidationException(String message){
        super("Product Id should be 8 digits and number. :" + message);
    }
}
