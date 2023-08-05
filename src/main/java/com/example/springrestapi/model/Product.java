package com.example.springrestapi.model;

import com.example.springrestapi.annotation.InRangeCustomAnnotation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Product {
    private Long productId;
    @NotBlank(message = "Name is a mandatory field")
    private String productName;
    @NotNull(message = "Price needs to be specified!")
    @InRangeCustomAnnotation(min=1, max=10)
    private Integer productPrice;

    public Product() {
    }

    public Product(Long productId, String productName, Integer productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProdcutId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }
}
