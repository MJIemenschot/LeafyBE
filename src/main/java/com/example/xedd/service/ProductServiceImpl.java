package com.example.xedd.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


import com.example.xedd.exception.RecordNotFoundException;
import com.example.xedd.model.Item;
import com.example.xedd.model.Product;
import com.example.xedd.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

//    @Override
//    public List<Product> getAllActiveProducts() {
//        return productRepository.findAll();
//    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void updateProduct(long id, Product product) {
        if (!productRepository.existsById(id)) throw new RecordNotFoundException();
        Product existingProduct = productRepository.findById(id).get();
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setImage(product.getImage());

        productRepository.save(existingProduct);

    }

//    @Override
//    public void partialUpdateProduct(long id, Map<String, String> fields) {
//        if (!productRepository.existsById(id)) throw new RecordNotFoundException();
//        Product product = productRepository.findById(id).get();
//        for (String field : fields.keySet()) {
//            switch (field.toLowerCase()) {
//                case "name":
//                    product.setName((String) fields.get(field));
//                    break;
//                case "description":
//                    product.setDescription((String) fields.get(field));
//                    break;
//                case "image":
//                    product.setImage((String) fields.get(field));
//
//            }
//        }
//        productRepository.save(product);
//
//    }

    @Override
    public void deleteProduct(long id) {
        if (!productRepository.existsById(id)) throw new RecordNotFoundException();
        productRepository.deleteById(id);
    }

}
