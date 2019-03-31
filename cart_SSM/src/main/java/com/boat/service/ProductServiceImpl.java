package com.boat.service;

import com.boat.dao.ProductDao;
import com.boat.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;
    public void addProduct(Product product) {
        productDao.addProduct(product);
    }

    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    public Product getProduct(int id) {
        return productDao.getProduct(id);
    }

    public List<Product> list() {
        return productDao.list();
    }

    public void deleteProduct(int id) {
        productDao.deleteProduct(id);
    }
}
