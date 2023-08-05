package com.example.springrestapi.controller;

import com.example.springrestapi.model.Product;
import com.example.springrestapi.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductServiceImpl productService;

    @GetMapping("")
    List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    Product getProductById(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PostMapping(value = "")
    public Map<String, Object> createProduct(@RequestParam(value = "id") Long id,
                                             @RequestParam(value = "name") String name,
                                             @RequestParam(value = "price") Integer price) {
        productService.createProduct(id, name, price);
        Map<String, Object> map = new HashMap<>();
        map.put("status", "Product added!");
        return map;
    }

    @PutMapping(value = "")
    public Product updateProductUsingJson(@RequestBody Product product) {
        productService.updateProduct(product);
        return product;
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        Map<String, Object> map = new HashMap<>();
        map.put("status", "Product deleted!");
        return map;
    }
}
