package com.demo.myretail.exception;

import com.demo.myretail.model.ProductMessage;

/**
 * Product ID not matching in JSON body
 */
public class ProductIdNotMatchingException extends RuntimeException {
    public ProductIdNotMatchingException(String message){
        super(ProductMessage.ERR103 + message);
    }
}
