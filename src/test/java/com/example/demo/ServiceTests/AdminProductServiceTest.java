package com.example.demo.ServiceTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.Entities.Inventory;
import com.example.demo.Entities.Price;
import com.example.demo.Entities.Product;
import com.example.demo.UserRepo.Repo;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.AdminProductService;

public class AdminProductServiceTest {

	@Mock
	private Repo repo;
	
	@InjectMocks
	private AdminProductService productservice;
	
	@BeforeEach
	public void set() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	
	public void addProductTests() {
		Product product= new Product(1, "Product1", "BrandA", "Description1", 
        		new Price("USD", 100.0), new Inventory(30,25,5), new ArrayList<>(), "electronics");
		when(repo.save(product)).thenReturn(product);
		
		Product result= productservice.addProduct(product);
		assertEquals(product,result);
		assertEquals("Product1", result.getName());
		assertEquals(100.0, result.getPrice().getAmount(),0.001);
		
	}
	
	@Test
	public void updateProductTests() {
		int id=1;
		Product existingproduct=new Product(id, "Existed Product", "Old Brand", "Description1", 
        		new Price("USD", 100.0), new Inventory(30,25,5), new ArrayList<>(), "electronics");
		
		Product updateproduct=new Product(id, "Updated Product", "New Brand", "Description1", 
        		new Price("USD", 100.0), new Inventory(30,25,5), new ArrayList<>(), "electronics");
		when(repo.findById(id)).thenReturn(Optional.of(existingproduct));
		when(repo.save(updateproduct)).thenReturn(updateproduct);
		
		
		Product result=productservice.updateProduct(1, updateproduct);
		assertEquals("New Brand",result.getBrand());
		assertEquals("Updated Product",result.getName());
		
		verify(repo).findById(id);
		verify(repo).save(existingproduct);
		
	}
	
	@Test
	public void updateProductNotFoundTests() {
		int id=1;
		Product updateproduct=new Product(id, "Updated Product", "New Brand", "Description1", 
        		new Price("USD", 100.0), new Inventory(30,25,5), new ArrayList<>(), "electronics");
		when(repo.findById(id)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class,()->productservice.updateProduct(id, updateproduct));
		
		
	}
	@Test
	public void removeProductTests() {
		int productId=1;
		productservice.removeProduct(productId);
		
		verify(repo, times(1)).deleteById(productId);
		
	}
}
