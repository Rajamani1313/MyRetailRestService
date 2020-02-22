package com.demo.MyRetailRestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public Product fetchProductDetails(Long id){
        Product product = productRepository.findByProductId(id);
//        System.out.println(product.toString());
        return productRepository.findByProductId(id);
    }
}
