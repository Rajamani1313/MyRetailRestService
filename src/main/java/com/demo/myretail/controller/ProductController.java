package com.demo.myretail.controller;

import com.demo.myretail.Exception.ProductIdNotMatchingException;
import com.demo.myretail.Exception.ProductIdValidationException;
import com.demo.myretail.Exception.ProductPriceNotFoundException;
import com.demo.myretail.model.Product;
import com.demo.myretail.model.ProductMessage;
import com.demo.myretail.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for Product API
 */
@RestController
@RequestMapping("/v1/products")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * Get product details for given product Id
     *
     * @param id
     * @return Product
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getProductDetails( @PathVariable String id) {

        /**
         * Validate Product Id to be numeric
         */
        String regEx = "^[0-9]{8}$";
        if (!id.matches(regEx)) {
            log.info(ProductMessage.ERR102 + id);
            throw new ProductIdValidationException(id);
        }
        /**
         * Fetch Product from persistent storage
         */
        Product product = null;
        product = productService.fetchProductDetails(Long.parseLong(id));
        log.info("Get Function Success: " + product.toString());
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    /**
     * Insert a new price record for product
     *
     * @param id
     * @param product
     * @return Response Object
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updatePriceDetails(@PathVariable String id, @RequestBody @Valid Product product) {

        /**
         * Validate Product Id to be numeric
         */
        String regEx = "^[0-9]{8}$";
        if (!id.matches(regEx)) {
            log.info(ProductMessage.ERR102 + id);
            throw new ProductIdValidationException(id);
        }

        /**
         * Validate Product Id to be matching the request URI
         */
        if (!(product.getId().equals(Long.parseLong(id)))) {
            log.info(ProductMessage.ERR103 + id + " vs " + product.getId());
            throw new ProductIdNotMatchingException(id + " vs " + product.getId());
        }

        /**
         * Insert Product Price details to persistent storage
         */
        productService.saveProductDetails(product);
        log.info("Insert Function Success: " + ProductMessage.MSG100 + id);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductMessage.MSG100 + id);
    }
}
