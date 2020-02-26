package com.demo.myretail.Exception;

import com.demo.myretail.model.ProductMessage;

public class ProductDescriptionNotFoundException extends RuntimeException {
    public ProductDescriptionNotFoundException(String id){
        super(ProductMessage.ERR101 + id);
    }
}
