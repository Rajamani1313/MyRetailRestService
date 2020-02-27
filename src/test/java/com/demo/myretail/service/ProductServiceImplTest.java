package com.demo.myretail.service;

import com.demo.myretail.exception.ProductDescriptionNotFoundException;
import com.demo.myretail.exception.ProductPriceNotFoundException;
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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test for Service Class
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductServiceImplTest {

    @TestConfiguration
    static class ProductServiceImplTestContextConfiguration {

        @Bean
        public ProductService productService() {
            return new ProductServiceImpl();
        }
    }

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    Long productId = 13860428L;
    Long testProductId = 12345678L;
    Product product = new Product();
    ProductPrice productPrice = new ProductPrice();
    String description = "The Big Lebowski (Blu-ray)";

    @Before
    public void setup() {
        product.setName("test");
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setValue(1.99);
        currentPrice.setCurrencyCode("USD");
        product.setCurrentPrice(currentPrice);
        product.setId(productId);
        productPrice.setProductId(productId);
        productPrice.setCurrency("USD");
        productPrice.setPrice(1.99);
        Mockito.when(productRepository.findByProductId(productId))
                .thenReturn(productPrice);
    }

    @Test
    public void test_valid_product_structure_should_save_successfully() {
        productService.saveProductDetails(product);
        MatcherAssert.assertThat(productId, Matchers.is(productRepository.findByProductId(productId).getProductId()));
    }

    @Test
    public void test_valid_product_should_return_success() {
        Product product = productService.fetchProductDetails(productId);
        MatcherAssert.assertThat(productId, Matchers.is(product.getId()));
    }

    @Test(expected = ProductPriceNotFoundException.class)
    public void test_invalid_product_should_return_price_not_found_exception() {
        Product product = productService.fetchProductDetails(testProductId);
    }

    @Test
    public void test_valid_product_id_should_return_description_success() {
        String result = productService.fetchProductName(productId);
        MatcherAssert.assertThat(description, Matchers.is(result));
    }

    @Test(expected = ProductDescriptionNotFoundException.class)
    public void test_invalid_product_id_should_return_description_not_found() {
        String result = productService.fetchProductName(testProductId);
    }

    @After
    public void rollback() {
        productRepository.deleteByProductId(testProductId);
    }

}
