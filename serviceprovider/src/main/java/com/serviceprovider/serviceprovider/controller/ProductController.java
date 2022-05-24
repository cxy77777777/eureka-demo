package com.serviceprovider.serviceprovider.controller;

import com.serviceprovider.serviceprovider.entity.Product;
import com.serviceprovider.serviceprovider.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("getList")
    public List<Product> getList(){
        List<Product>list =  productService.getList();
        return list;
    }
}
