package com.example.springrestapi.service;

import com.example.springrestapi.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl {

    static List<Product> products = new ArrayList<>();

    public ProductServiceImpl() {
        products.add(new Product(1L, "Iphone", 1999));
        products.add(new Product(2L, "Watch", 599));
        products.add(new Product(3L, "PC", 1599));
    }

    public List<Product> getProducts() {
        List<Product> products = getProductList();
        return products;
    }

    private static List<Product> getProductList() {
        return products;
    }

    public void createProduct(Long productId, String productName, Integer price) {
        products.add(new Product(productId, productName, price));
    }

    public Product getProduct(Long id){
        Iterator<Product> iterator = getProductList().iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public void updateProduct(Product product) {
        getProduct(product.getProductId()).setProductName(product.getProductName());
        getProduct(product.getProductId()).setProductPrice(product.getProductPrice());
    }

    public void deleteProduct(Long id) {
        System.out.println("Status.. " + products.remove(getProduct(id)));
    }

}