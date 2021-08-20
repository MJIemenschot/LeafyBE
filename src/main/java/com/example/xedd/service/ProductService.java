package com.example.xedd.service;

import com.example.xedd.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    void saveProduct(Product product);
    List<Product> getProducts();
    Optional<Product> getProductById(Long id);
   // void partialUpdateProduct(long id, Map<String, String> fields);
    void updateProduct(long id, Product product);
    void deleteProduct(long id);
}
