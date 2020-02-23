package com.demo.MyRetailRestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @param id
     * @return Product
     */
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Product findById(@PathVariable String id){
        Product product = productService.fetchProductDetails(Long.parseLong(id));
        return product;
    }

    /**
     * Insert a new price record for product
     * @param id
     * @param product
     * @return Response Object
     */
    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updatePrice(@PathVariable String id,@RequestBody Product product){
        productService.saveProductDetails(Long.parseLong(id),product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product " +id+ " Created Successfully");
    }
}
