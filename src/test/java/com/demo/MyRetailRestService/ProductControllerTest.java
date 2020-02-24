package com.demo.MyRetailRestService;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc //need this in Spring Boot test
public class ProductControllerTest {

    long id = 13860428;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetProductDetails() throws Exception {
        mockMvc.perform(get("/v1/products/{id}",id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addProduct() throws Exception{
        Product product = new Product();
        product.setId(id);
        product.setName("test");
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setValue(1.99);
        currentPrice.setCurrency_code("USD");
        product.setCurrent_price(currentPrice);
        Gson gson = new Gson();
        String json = gson.toJson(product);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/v1/products/"+id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json);
        System.out.println(builder.toString());
        this.mockMvc.perform(builder).andExpect(status().isConflict());

    }
}
