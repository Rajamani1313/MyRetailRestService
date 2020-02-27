package com.demo.myretail.exception;

import com.demo.myretail.model.ProductMessage;

/**
 * Product Not found in DB
 */
public class ProductPriceNotFoundException extends RuntimeException {
    public ProductPriceNotFoundException(String message){
        super(ProductMessage.ERR100 + message);
    }
}
