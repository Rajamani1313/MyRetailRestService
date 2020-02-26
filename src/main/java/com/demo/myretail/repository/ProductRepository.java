package com.demo.myretail.repository;

import com.demo.myretail.dto.ProductPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for Mongo DB Products Table
 */
@Repository
public interface ProductRepository extends MongoRepository<ProductPrice,Long> {
    ProductPrice findByProductId(Long id);
    void deleteByProductId(Long id);
}
