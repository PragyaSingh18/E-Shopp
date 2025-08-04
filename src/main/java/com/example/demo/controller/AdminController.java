package com.example.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.Product;
import com.example.demo.apiResponse.APIResponse;
import com.example.demo.service.AdminProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/products")
public class AdminController {
	
    @Autowired
    private AdminProductService adminProductService;

    
    @PostMapping("/add")
    public ResponseEntity<APIResponse> addProduct(@Valid @RequestBody Product product) {
        Product products = adminProductService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse("Product added successfully",products,HttpStatus.CREATED.value(), UUID.randomUUID().toString()));
    }
    

    @PutMapping("/update/{Id}")
    public ResponseEntity<APIResponse> updateProduct(@PathVariable("Id") Integer Id, 
                                                     @Valid @RequestBody Product product) {
        Product updatedProduct = adminProductService.updateProduct(Id, product);
        return ResponseEntity.ok(new APIResponse("Product updated successfully",updatedProduct,HttpStatus.OK.value(), UUID.randomUUID().toString()));
    }

    @DeleteMapping("/remove/{Id}")
    public ResponseEntity<APIResponse> removeProduct(@PathVariable("Id") Integer Id) {
        adminProductService.removeProduct(Id);
        return ResponseEntity.ok(new APIResponse("Product removed successfully",null,HttpStatus.OK.value(), UUID.randomUUID().toString()));

}
}
