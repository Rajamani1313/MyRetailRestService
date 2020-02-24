package com.demo.MyRetailRestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Controller for Product API
 */
@RestController
@RequestMapping("/v1/products")
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
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ProductMessage.ERR102);
        }
        /**
         * Fetch Product from persistent storage
         */
        Product product = null;
        try {
            product = productService.fetchProductDetails(productId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
    public ResponseEntity UpdatePriceDetails(@PathVariable String id, @RequestBody Product product) {


        /**
         * Validate Product Id to be numeric
         */
        Long productId = null;
        try {
            productId = Long.parseLong(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ProductMessage.ERR102);
        }

        /**
         * Validate Product Id to be matching the request URI
         */
        if (!(product.getId().equals(productId)))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ProductMessage.ERR103);

        /**
         * Validate Request Body Json for Constraints
         */
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object>> errorList = validator.validate(product);
        if (!errorList.isEmpty()) {
            List message = new ArrayList();
            for (ConstraintViolation error : errorList)
                message.add(error.getPropertyPath().toString() + ":" + error.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ProductMessage.ERR105 + "\n" + Arrays.toString(message.toArray()));
        }

        /**
         * Insert Product Price details to persistent storage
         */
        try {
            productService.saveProductDetails(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage() + id);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductMessage.MSG100 + id);
    }
}
