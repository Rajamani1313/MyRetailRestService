package com.demo.myretail.controller;

import com.demo.myretail.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for Home Page
 */
@RestController
@RequestMapping("/v1/home")
public class HomeController {

    @Autowired
    private ProductService productService;
    
    @GetMapping("")
    public String home(){
        return "<h1 style=\"color:Tomato;\">Welcome to Product API<h1>";
    }
}
