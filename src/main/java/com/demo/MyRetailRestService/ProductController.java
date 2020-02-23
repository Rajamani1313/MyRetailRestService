package com.demo.MyRetailRestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for Product API
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * Get product details for given product Id
     * @param id
     * @return Product
     */
    @GetMapping("/{id}")
    public Product findById(@PathVariable String id){
        Product product = productService.fetchProductDetails(Long.parseLong(id));
        return product;
    }
}
