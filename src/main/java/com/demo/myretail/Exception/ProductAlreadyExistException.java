package com.demo.myretail.Exception;

import com.demo.myretail.model.ProductMessage;

public class ProductAlreadyExistException extends RuntimeException {
    public ProductAlreadyExistException(String id){
        super(ProductMessage.ERR104 + id);
    }
}
