package com.example.demo.ControllerTests;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.Entities.Inventory;
import com.example.demo.Entities.Price;
import com.example.demo.Entities.Product;
import com.example.demo.controller.AdminController;
import com.example.demo.service.AdminProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

	@Mock
	private AdminProductService adminProductService;
	
	@InjectMocks
	private AdminController admincontroller;
	
	@Autowired
	private MockMvc mockmvc;
	
	@BeforeEach
	
	public void setUp() {
		//MockitoAnnotations.openMocks(this);
		//admincontroller=new AdminController(adminProductService);
		mockmvc=MockMvcBuilders.standaloneSetup(admincontroller).build();
	}
	
	@Test
	
	public void addProductTest() throws JsonProcessingException, Exception {
		Product product= new Product(1, "Product1", "BrandA", "Description1", 
        		new Price("USD", 100.0), new Inventory(30,25,5), new ArrayList<>(), "electronics");
		when(adminProductService.addProduct(product)).thenReturn(product);
		
		mockmvc.perform(post("/api/admin/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product)))  
                .andExpect(status().isCreated())  
                .andExpect(jsonPath("$.message").value("Product added successfully"))  
                .andExpect(jsonPath("$.statusCode").value(201))  
                .andExpect(jsonPath("$.traceId").isNotEmpty()) 
		.andExpect(jsonPath("$.data.name").value("Product1"))  
        .andExpect(jsonPath("$.data.brand").value("BrandA"))  
        .andExpect(jsonPath("$.data.price.amount").value(100.0)); 
		
		verify(adminProductService).addProduct(product);

				
	}
	
	@Test
	public void updateProductTest() throws JsonProcessingException, Exception {
		int id=1;
		Product existingproduct=new Product(id, "Existed Product", "Old Brand", "Description1", 
        		new Price("USD", 100.0), new Inventory(30,25,5), new ArrayList<>(), "electronics");
		
		Product updateproduct=new Product(id, "Updated Product", "New Brand", "Description1", 
        		new Price("USD", 100.0), new Inventory(30,25,5), new ArrayList<>(), "electronics");
		
		when(adminProductService.updateProduct(1, updateproduct)).thenReturn(updateproduct);
		mockmvc.perform(put("/api/admin/products/update/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(updateproduct)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Product updated successfully"))  
                .andExpect(jsonPath("$.statusCode").value(200)) 
                .andExpect(jsonPath("$.traceId").isNotEmpty());
		
		verify(adminProductService).updateProduct(id, updateproduct);
		
	}
	
	@Test
	
	public void removeProductTest() throws Exception {
		int id=1;
		doNothing().when(adminProductService).removeProduct(id);
		mockmvc.perform(delete("/api/admin/products/remove/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.message").value("Product removed successfully"))  
        .andExpect(jsonPath("$.statusCode").value(200))  
        .andExpect(jsonPath("$.traceId").isNotEmpty()); 
		
		verify(adminProductService).removeProduct(id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
