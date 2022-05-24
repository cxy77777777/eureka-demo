package com.serviceprovider.serviceprovider.service.Impl;

import com.serviceprovider.serviceprovider.entity.Product;
import com.serviceprovider.serviceprovider.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public List<Product> getList() {
        List<Product> list = new ArrayList<>();
        Product product = new Product();
        product.setId(1);
        product.setProductName("华为手机");
        product.setProductNum(2);
        product.setProductPrice(5.32);
        list.add(product);
        return list;
    }
}
