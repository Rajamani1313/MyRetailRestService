package com.demo.myretail.service;

import com.demo.myretail.exception.ProductAlreadyExistException;
import com.demo.myretail.model.Product;
import org.springframework.stereotype.Service;

/**
 * Service Interface to execute functionality for Products
 */
@Service
public interface ProductService {



    /**
     * Gets price from Database and Name from external API
     *
     * @param id
     * @return Product
     */
     Product fetchProductDetails(Long id) throws ProductAlreadyExistException ;

    /**
     * Fetches Product Name from Redsky External API by passing Product Id
     *
     * @param id
     * @return Product Name
     */
     String fetchProductName(Long id) throws ProductAlreadyExistException ;

    /**
     * Inserts a Price record for the product
     *
     * @param product
     */
     void saveProductDetails(Product product) throws ProductAlreadyExistException ;

}
