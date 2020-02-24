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
     *
     * @param id
     * @return Product
     */
    public Product fetchProductDetails(Long id) throws Exception {
        ProductPrice productPrice = productRepository.findByProductId(id);
        if (productPrice == null) {
            throw new Exception(ProductMessage.ERR100);
        }
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
     *
     * @param id
     * @return Product Name
     */
    private String fetchProductName(Long id) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = null;
        String description = null;
        try {
            responseEntity = restTemplate.getForEntity(productDescriptionUri, String.class, id);
            System.out.println("Sysout" + responseEntity);
            description = JsonPath.parse(responseEntity.getBody()).read("$.product.item.product_description.title").toString();
        } catch (Exception e) {
            throw new Exception(ProductMessage.ERR101);
        }
        return description;
    }

    /**
     * Inserts a Price record for the product
     *
     * @param product
     */
    public void saveProductDetails(Product product) throws Exception{
        ProductPrice productPrice = new ProductPrice();

        productPrice.setProductId(product.getId());
        productPrice.setPrice(product.getCurrent_price().getValue());
        productPrice.setCurrency(product.getCurrent_price().getCurrency_code());
        try {
            productRepository.save(productPrice);
        }catch (Exception e){
            System.out.println("Exception:"+e.getMessage());
            throw new Exception(ProductMessage.ERR104);
        }
    }
}
