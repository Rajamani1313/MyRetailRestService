package com.demo.MyRetailRestService;

import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service Class to execute functionality for Products
 */
@Service
public class ProductService {

    @Value("${external.api.uri}")
    private String productDescriptionUri;

    @Autowired
    ProductRepository productRepository;

    /**
     * Gets price from Database and Name from external API
     * @param id
     * @return Product
     */
    public Product fetchProductDetails(Long id){
        ProductPrice productPrice = productRepository.findByProductId(id);
        Product product = new Product();
        product.setName(fetchProductName(id));
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setCurrency_code(productPrice.getCurrency());
        currentPrice.setValue(productPrice.getPrice());
        product.setCurrent_price(currentPrice);
        product.setId(id);
        return product;
    }

    /**
     * Fetches Product Name from Redsky External API by passing Product Id
     * @param id
     * @return Product Name
     */
    private String fetchProductName(Long id){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = null;
        responseEntity = restTemplate.getForEntity(productDescriptionUri, String.class, id);
        String description = JsonPath.parse(responseEntity.getBody()).read("$.product.item.product_description.title").toString();
        return description;
    }

    /**
     * Inserts a Price record for the product
     * @param id
     * @param product
     */
    public void saveProductDetails(Long id, Product product) {
        ProductPrice productPrice = new ProductPrice();
        productPrice.setProductId(id);
        productPrice.setPrice(product.getCurrent_price().getValue());
        productPrice.setCurrency(product.getCurrent_price().getCurrency_code());
        productRepository.save(productPrice);

    }
}
