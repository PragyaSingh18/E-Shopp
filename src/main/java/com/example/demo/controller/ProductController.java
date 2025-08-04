package com.example.demo.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Entities.ProductDTO;
import com.example.demo.apiResponse.APIResponse;
import com.example.demo.exception.ProductNotAvailableException;
import com.example.demo.service.ProductService;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    

    @GetMapping("/category/{category}")
    public ResponseEntity<APIResponse> getProductByCategory(@PathVariable("category") String category, 
                                                             @RequestParam (defaultValue = "inventory") String sortBy) throws ProductNotAvailableException {
        List<ProductDTO> products = productService.getProductsByCategory(category, sortBy);
        if (products==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse("No products with sufficient inventory.",null,HttpStatus.NOT_FOUND.value(), UUID.randomUUID().toString()));
        }
        return ResponseEntity.ok(new APIResponse("Product Found!!",products, HttpStatus.OK.value(), UUID.randomUUID().toString()));
    }
    
}