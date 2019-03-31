package com.boat.service;

import com.boat.dao.ProductDao;
import com.boat.entity.Product;

import java.util.List;


public interface ProductService extends ProductDao {
    void addProduct(Product product);

    void deleteProduct(int id);

    void updateProduct(Product product);

    Product getProduct(int id);

    List<Product> list();
}
