package com.demo.myretail.Exception;

import com.demo.myretail.model.ProductMessage;

public class ProductPriceNotFoundException extends RuntimeException {
    public ProductPriceNotFoundException(String message){
        super(ProductMessage.ERR100 + message);
    }
}
