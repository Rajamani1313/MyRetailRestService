package com.demo.myretail.exception;

import com.demo.myretail.model.ProductMessage;

/**
 * Invalid Product ID exception
 */
public class ProductIdValidationException extends RuntimeException {
    public ProductIdValidationException(String message){
        super(ProductMessage.ERR105 + message);
    }
}
