package com.example.xedd.controller;

import java.io.BufferedOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.example.xedd.exception.NotFoundException;
import com.example.xedd.model.Item;
import com.example.xedd.model.Product;
import com.example.xedd.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping
public class ProductController {

    //    @Value("${uploadDir}")
   // private String uploadFolder;

    @Value("${app.upload.dir:${user.home}}")
//@Value("${uploads}")
    private String uploads;  // relative to root
    ///or
    //private String uploadDirectory;  // relative to root
    private final Path upload = Paths.get(".\\uploads");

    @Autowired
    private ProductService productService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @PostMapping(value="/product/save")
    public @ResponseBody ResponseEntity<?> saveProduct(@RequestParam("name") String name,
                                                       //@RequestParam("difficulty") enum difficulty,
                                                       @RequestParam("description") String description,
                                                       Model model, HttpServletRequest request
            ,final @RequestParam("image") MultipartFile file) {
        try {
            //String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
            String uploadDirectory = request.getServletContext().getRealPath(uploads);
            log.info("uploadDirectory:: " + uploadDirectory);
            String fileName = file.getOriginalFilename();
            String filePath = Paths.get(uploadDirectory, fileName).toString();
            log.info("FileName: " + file.getOriginalFilename());
            if (fileName == null || fileName.contains("..")) {
                model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
                return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
            }
            String[] names = name.split(",");
            String[] descriptions = description.split(",");
            Date createDate = new Date();
            log.info("Name: " + names[0]+" "+filePath);
            log.info("description: " + descriptions[0]);
            //log.info("price: " + price);
            try {
                File dir = new File(uploadDirectory);
                if (!dir.exists()) {
                    log.info("Folder Created");
                    dir.mkdirs();
                }
                // Save the file locally
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                stream.write(file.getBytes());
                stream.close();
            } catch (Exception e) {
                log.info("in catch");
                e.printStackTrace();
            }
            byte[] imageData = file.getBytes();
            Product product = new Product();
            product.setName(names[0]);
            product.setImage(imageData);
            //product.setDifficulty(difficulty);
            product.setDescription(descriptions[0]);
            product.setCreateDate(createDate);
            productService.saveProduct(product);
            log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
            return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/product/display/{id}")
    @ResponseBody
    void showImage(@PathVariable("id") Long id, HttpServletResponse response, Optional<Product> product)
            throws NotFoundException, IOException {
        log.info("Id :: " + id);
        product = productService.getProductById(id);
    //response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(product.get().getImage());
        response.getOutputStream().close();
    }

    @GetMapping(value="products")
    public ResponseEntity<Object> getProducts()  { return ResponseEntity.ok().body(productService.getProducts()); }

    @GetMapping(value = "/product/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(productService.getProductById(id));
    }

    @PutMapping(value = "/product/update/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
        productService.updateProduct(id, product);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}