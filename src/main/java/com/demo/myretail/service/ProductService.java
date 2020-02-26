package com.demo.myretail.service;

import com.demo.myretail.Exception.ProductAlreadyExistException;
import com.demo.myretail.Exception.ProductDescriptionNotFoundException;
import com.demo.myretail.Exception.ProductPriceNotFoundException;
import com.demo.myretail.dto.ProductPrice;
import com.demo.myretail.model.CurrentPrice;
import com.demo.myretail.model.Product;
import com.demo.myretail.repository.ProductRepository;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service Class to execute functionality for Products
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
