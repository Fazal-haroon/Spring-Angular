package com.example.springrestapi.controller;

import com.example.springrestapi.model.Product;
import com.example.springrestapi.service.FileStorageService;
import com.example.springrestapi.service.ProductServiceImpl;
import com.example.springrestapi.util.UploadFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.core.io.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    private FileStorageService fileStorageService;

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

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/product/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println(("Could not determine file type."));
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
