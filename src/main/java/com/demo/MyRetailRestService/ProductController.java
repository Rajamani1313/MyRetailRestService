package com.demo.MyRetailRestService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/products/{id}")
    public Item findById(@PathVariable String id){
        Item item = new Item();
        item.setName("test"+id);
        return item;
    }
}
