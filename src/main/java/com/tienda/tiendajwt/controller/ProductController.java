package com.tienda.tiendajwt.controller;

import com.tienda.tiendajwt.entity.ImageModel;
import com.tienda.tiendajwt.entity.Product;
import com.tienda.tiendajwt.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('Admin')")
    @PostMapping(value = {"/addNewProduct"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product addNewProduct(@RequestPart("product") Product product,
                                 @RequestPart("imageFile") MultipartFile[] file) {
        try{
            Set<ImageModel> images= uploadImage(file);
            product.setProductImages(images);
            return productService.addNewProduct(product);

        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<ImageModel> imageModels = new HashSet<>();

        for(MultipartFile file : multipartFiles){
            ImageModel imageModel = new ImageModel(file.getOriginalFilename(), file.getContentType(),
                    file.getBytes());

            // Agregando la imagen al Conjunto de Imagenes de un Producto
            imageModels.add(imageModel);
        }
        return imageModels;
    }

    @GetMapping({"/getAllProducts"})
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping({"/deleteProductDetails/{productId}"})
    public void deleteProductDetails(@PathVariable("productId") Integer productId){
        productService.deleteProductDetails(productId);
    }

    @GetMapping({"/getProductDetailsById/{productId}"})
    public Product getProductDetailsById(@PathVariable("productId") Integer productId){
        return productService.getProductDetailsById(productId);
    }

}
