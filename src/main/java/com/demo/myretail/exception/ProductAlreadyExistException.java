package com.demo.myretail.exception;

import com.demo.myretail.model.ProductMessage;

/**
 * Product Already found in Persistence
 */

public class ProductAlreadyExistException extends RuntimeException {
    public ProductAlreadyExistException(String id){
        super(ProductMessage.ERR104 + id);
    }
}
