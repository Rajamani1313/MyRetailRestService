package com.demo.MyRetailRestService;

import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductService {

    @Value("${external.api.uri}")
    private String productDescriptionUri;

    @Autowired
    ProductRepository productRepository;

    public Product fetchProductDetails(Long id){
        Price price = productRepository.findByProductId(id);
//        System.out.println(product.toString());
        Product product = new Product();
        product.setName(fetchProductDescription(id));
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setCurrency_code(price.getCurrency());
        currentPrice.setValue(price.getPrice());
        product.setCurrent_price(currentPrice);
        product.setId(id);
        return product;
    }
    private String fetchProductDescription(Long id){

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = null;
        System.out.println("Id:"+id);
        System.out.println("URL:"+productDescriptionUri);
        responseEntity = restTemplate.getForEntity(productDescriptionUri, String.class, id);
        String description = JsonPath.parse(responseEntity.getBody()).read("$.product.item.product_description.title").toString();
        return description;
    }
}
