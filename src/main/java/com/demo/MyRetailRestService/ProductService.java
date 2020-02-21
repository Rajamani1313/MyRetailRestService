package com.demo.MyRetailRestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public Product fetchProductDetails(Long id){
        return productRepository.findByProductId(id);
    }
}
