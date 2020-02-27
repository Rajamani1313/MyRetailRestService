package com.demo.myretail.controller;

import com.demo.myretail.exception.ProductPriceNotFoundException;
import com.demo.myretail.model.CurrentPrice;
import com.demo.myretail.model.Product;
import com.demo.myretail.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for Product Controller
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    /**
     * product Id Values for Testing
     */
    long tempId = 99999999;
    long getId = 13860427;
    long smallId = 1386042;
    long largeId = 1386042999;
    String stringId = "abcdef";

    Product product = new Product();
    CurrentPrice currentPrice = new CurrentPrice();
    ObjectMapper obj = new ObjectMapper();
    String json;
    MockHttpServletRequestBuilder builder;

    /**
     * Setup for adding new Put Request
     * @param product
     * @return
     */
    private Product setup(Product product) {
        product.setName("test");
        currentPrice.setValue(1.99);
        currentPrice.setCurrencyCode("USD");
        product.setCurrentPrice(currentPrice);
        return product;
    }

    @Test
    public void test_get_product_details_valid_id_should_return_ok() throws Exception {
        mockMvc.perform(get("/v1/products/{id}", getId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(getId))));
    }

    @Test
    public void test_get_product_details_invalid_id_should_return_not_found() throws Exception {
        mockMvc.perform(get("/v1/products/{id}", tempId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Product Price not available")));
    }

    @Test
    public void test_get_product_details_string_should_return_bad_request() throws Exception {
        mockMvc.perform(get("/v1/products/{id}", stringId))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Product Id should be in 8 digits.")));
    }

    @Test
    public void test_get_product_details_small_id_should_return_not_found() throws Exception {
        mockMvc.perform(get("/v1/products/{id}", smallId))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Product Id should be in 8 digits.")));
    }

    @Test
    public void test_get_product_details_large_id_should_return_not_found() throws ProductPriceNotFoundException,Exception {
        mockMvc.perform(get("/v1/products/{id}", largeId))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Product Id should be in 8 digits.")));
    }

    @Test
    public void add_product_valid_id_should_return_ok() throws Exception {
        product.setId(tempId);
        product = setup(product);
        json = obj.writeValueAsString(product);
        builder = MockMvcRequestBuilders.put("/v1/products/" + tempId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json);
        this.mockMvc.perform(builder).andExpect(status().isCreated())
                .andExpect(content().string(containsString("Product Created Successfully")));
        productRepository.deleteByProductId(tempId);
    }

    @Test
    private void add_product_existing_id_should_return_conflict() throws Exception {
        product.setId(getId);
        product = setup(product);
        json = obj.writeValueAsString(product);
        builder = MockMvcRequestBuilders.put("/v1/products/" + getId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json);
        this.mockMvc.perform(builder).andExpect(status().isConflict())
                .andExpect(content().string(containsString("Product Id already existing")));
    }

    @Test
    public void add_product_mismatch_id_should_return_bad_request() throws Exception {
        product.setId(getId);
        product = setup(product);
        json = obj.writeValueAsString(product);
        builder = MockMvcRequestBuilders.put("/v1/products/" + tempId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json);
        this.mockMvc.perform(builder).andExpect(status().isConflict())
                .andExpect(content().string(containsString("Product Id in the body is not matching.")));
    }

    @Test
    public void add_product_string_id_should_return_bad_request() throws Exception {
        product.setId(tempId);
        product = setup(product);
        json = obj.writeValueAsString(product);
        builder = MockMvcRequestBuilders.put("/v1/products/" + stringId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json);
        this.mockMvc.perform(builder).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Product Id should be in 8 digits.")));
    }

    @Test
    public void add_product_small_id_should_return_bad_request() throws Exception {
        product.setId(smallId);
        product = setup(product);
        json = obj.writeValueAsString(product);
        builder = MockMvcRequestBuilders.put("/v1/products/" + smallId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json);
//        System.out.println(builder.toString());
        this.mockMvc.perform(builder).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Product Id should be greater than 10000000")));
    }

    @Test
    public void add_product_large_id_should_return_bad_request() throws Exception {
        product.setId(largeId);
        product = setup(product);
        json = obj.writeValueAsString(product);
        builder = MockMvcRequestBuilders.put("/v1/products/" + largeId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json);
        this.mockMvc.perform(builder).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Product Id should be less than 99999999")));
    }
}
