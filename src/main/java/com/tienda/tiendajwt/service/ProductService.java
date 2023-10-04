package com.tienda.tiendajwt.service;

import com.tienda.tiendajwt.dao.ProductDao;
import com.tienda.tiendajwt.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public Product addNewProduct(Product product) {
        return productDao.save(product);
    }

    public void deleteProductDetails(Integer id) {
        productDao.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return (List<Product>) productDao.findAll();
    }

    public Product getProductDetailsById(Integer id) {
        return productDao.findById(id).get();
    }

}
