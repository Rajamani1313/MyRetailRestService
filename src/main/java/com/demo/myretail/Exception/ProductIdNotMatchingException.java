package com.demo.myretail.Exception;

import com.demo.myretail.model.ProductMessage;

public class ProductIdNotMatchingException extends RuntimeException {
    public ProductIdNotMatchingException(String message){
        super(ProductMessage.ERR103 + message);
    }
}
