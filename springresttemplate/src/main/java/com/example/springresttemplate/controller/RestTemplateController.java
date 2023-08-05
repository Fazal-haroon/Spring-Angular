package com.example.springresttemplate.controller;

import com.example.springresttemplate.dto.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("/rest-template")
public class RestTemplateController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<String> getProduct() {
        String resourceUrl = "http://localhost:8082/product/";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);

        return response;
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable("id") Long id) throws JsonProcessingException {
        String resourceUrl = "http://localhost:8082/product";
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl + "/" + id, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonRootNode = mapper.readTree(response.getBody());
        JsonNode name = jsonRootNode.get("productName");
        return name.asText();
    }

    @PostMapping(value = "")
    public Map<String, Object> createProduct(@RequestBody Product product) {
        String resourceUrl = "http://localhost:8082/product/";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(resourceUrl)
                .queryParam("id", product.getProductId())
                .queryParam("name", product.getProductName())
                .queryParam("price", product.getProductPrice());

        Map<String, Object> productResponse = restTemplate.postForObject(builder.toUriString(), HttpEntity.EMPTY, Map.class);
        /*
         * restTemplate.exchange(builder.toUriString(), HttpMethod.POST, HttpEntity.EMPTY, Map.class);
         * */
        return productResponse;
    }

    @PutMapping(value = "")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        String resourceUrl = "http://localhost:8082/product/";

        HttpEntity<Product> request = new HttpEntity<>(product);

        ResponseEntity<Product> response = restTemplate.exchange(resourceUrl, HttpMethod.PUT, request, Product.class);

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map> deleteProduct(@PathVariable("id") Long id) {
        String resourceUrl = "http://localhost:8082/product/" + id;

        ResponseEntity<Map> response = restTemplate.exchange(resourceUrl, HttpMethod.DELETE, HttpEntity.EMPTY, Map.class);

        return response;
    }
}
