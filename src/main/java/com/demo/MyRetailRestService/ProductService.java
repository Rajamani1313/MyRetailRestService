package com.demo.MyRetailRestService;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service Class to execute functionality for Products
 */
@Service
@Slf4j
public class ProductService {

    @Value("${external.api.uri}")
    private String productDescriptionUri;

    @Autowired
    ProductRepository productRepository;

    /**
     * Gets price from Database and Name from external API
     *
     * @param id
     * @return Product
     */
    public Product fetchProductDetails(Long id) throws Exception {
        log.debug("Inside Fetch Product Details");
        log.debug("Product Id : " + id);
        ProductPrice productPrice = productRepository.findByProductId(id);
        if (productPrice == null) {
            log.info("Product not found in DB");
            throw new Exception(ProductMessage.ERR100);
        }
        log.debug("Price Details fetched from DB successfully" + productPrice.toString());
        Product product = new Product();
        product.setName(fetchProductName(id));
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setCurrency_code(productPrice.getCurrency());
        currentPrice.setValue(productPrice.getPrice());
        product.setCurrent_price(currentPrice);
        product.setId(id);
        log.debug("Product Retrieved successfully" + product.toString());
        return product;
    }

    /**
     * Fetches Product Name from Redsky External API by passing Product Id
     *
     * @param id
     * @return Product Name
     */
    private String fetchProductName(Long id) throws Exception {
        log.debug("Inside Fetch Product Name");
        log.debug("Product Id : " + id);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = null;
        String description = null;
        try {
            log.debug(productDescriptionUri);
            responseEntity = restTemplate.getForEntity(productDescriptionUri, String.class, id);
            log.debug("Response Entity: " + responseEntity);
            description = JsonPath.parse(responseEntity.getBody()).read("$.product.item.product_description.title").toString();
            log.debug("Description fetched succesfully:" + description);
        } catch (Exception e) {
            log.error("Exception:" + e.getMessage());
            throw new Exception(ProductMessage.ERR101);
        }
        return description;
    }

    /**
     * Inserts a Price record for the product
     *
     * @param product
     */
    public void saveProductDetails(Product product) throws Exception {
        log.debug("Inside Save Product Details");
        log.debug("Product: " + product.toString());
        ProductPrice productPrice = new ProductPrice();
        productPrice.setProductId(product.getId());
        productPrice.setPrice(product.getCurrent_price().getValue());
        productPrice.setCurrency(product.getCurrent_price().getCurrency_code());
        try {
            productRepository.save(productPrice);
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage());
            throw new Exception(ProductMessage.ERR104);
        }
    }
}
