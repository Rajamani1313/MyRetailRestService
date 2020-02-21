package com.demo.MyRetailRestService;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product,Long> {
    Product findByProductId(Long id);
}
