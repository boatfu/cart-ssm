package com.boat.dao;

import com.boat.entity.Product;

import java.util.List;

public interface ProductDao {
    void addProduct(Product product);
    void deleteProduct(int id );
    void updateProduct(Product product);
    Product getProduct(int id);
    List<Product> list();
}
