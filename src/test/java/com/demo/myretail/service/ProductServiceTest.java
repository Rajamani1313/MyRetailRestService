package com.demo.myretail.service;

import com.demo.myretail.dto.ProductPrice;
import com.demo.myretail.model.CurrentPrice;
import com.demo.myretail.model.Product;
import com.demo.myretail.repository.ProductRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    Long productId = 13860428L;
    Long testProductId = 12345678L;
    Product product = new Product();
    String description = "The Big Lebowski (Blu-ray)";

    @Before
    public void setup() {
        product.setName("test");
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setValue(1.99);
        currentPrice.setCurrencyCode("USD");
        product.setCurrentPrice(currentPrice);
        product.setId(testProductId);
        ProductPrice productPrice = new ProductPrice();
        productPrice.setProductId(testProductId);
        productPrice.setCurrency("USD");
        productPrice.setPrice(1.99);
    }

    @Test
    public void test_valid_product_structure_should_save_successfully() {
        productService.saveProductDetails(product);
        MatcherAssert.assertThat(testProductId, Matchers.is(productRepository.findByProductId(testProductId).getProductId()));
    }

    @After
    public void rollback() {
        productRepository.deleteByProductId(testProductId);
    }

    @Test
    public void test_valid_product_should_return_success() {
        Product product = productService.fetchProductDetails(productId);
        MatcherAssert.assertThat(productId, Matchers.is(product.getId()));
    }
    @Test
    public void test_valid_product_id_should_return_description_success() {
        String result = productService.fetchProductName(productId);
        MatcherAssert.assertThat(description, Matchers.is(result));
    }
}
