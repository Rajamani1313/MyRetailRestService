package com.demo.myretail.controller;

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
    public ResponseEntity getProductDetails(@PathVariable String id) {

        /**
         * Validate Product Id to be numeric
         */
        Long productId = null;
        try {
            productId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            log.info(ProductMessage.ERR102 + " - " + id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ProductMessage.ERR102);
        }
        /**
         * Fetch Product from persistent storage
         */
        Product product = null;
        try {
            product = productService.fetchProductDetails(productId);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
        Long productId = null;
        try {
            productId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            log.info(ProductMessage.ERR102 + " - " + id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ProductMessage.ERR102);
        }

        /**
         * Validate Product Id to be matching the request URI
         */
        if (!(product.getId().equals(productId))) {
            log.info(ProductMessage.ERR103 + " - " + id + " - " + product.getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ProductMessage.ERR103);
        }

        /**
         * Insert Product Price details to persistent storage
         */
        try {
            productService.saveProductDetails(product);
        } catch (Exception e) {
            log.info(e.getMessage() + id);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        log.info("Insert Function Success: " + ProductMessage.MSG100 + id);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductMessage.MSG100 + id);
    }
}
