package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.example.demo.Entities.Product;
import com.example.demo.UserRepo.Repo;
import com.example.demo.exception.ResourceNotFoundException;

import jakarta.validation.Valid;

@Service
public class AdminProductService {

	@Autowired
	private Repo repo;
	

	
	public Product addProduct(@Valid Product product) {
        return repo.save(product);
    }
	
	public Product updateProduct(Integer id, Product product) {
        Product existingproduct =repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        existingproduct.setName(product.getName());
        existingproduct.setBrand(product.getBrand());
        existingproduct.setDescription(product.getDescription());
        existingproduct.setPrice(product.getPrice());
        existingproduct.setInventory(product.getInventory());
        existingproduct.setAttributes(product.getAttributes());
        return repo.save(existingproduct);
    }

   public void removeProduct(Integer Id) {
    	repo.deleteById(Id);
    }
    

}
