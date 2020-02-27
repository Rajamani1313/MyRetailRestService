package com.demo.myretail.exception;

import com.demo.myretail.model.ProductMessage;

/**
 * Product Description not found external DB
 */
public class ProductDescriptionNotFoundException extends RuntimeException {
    public ProductDescriptionNotFoundException(String id){
        super(ProductMessage.ERR101 + id);
    }
}
