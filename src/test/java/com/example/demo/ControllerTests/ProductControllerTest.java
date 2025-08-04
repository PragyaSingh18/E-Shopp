package com.example.demo.ControllerTests;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.Entities.Inventory;
import com.example.demo.Entities.Price;
import com.example.demo.Entities.ProductDTO;
import com.example.demo.controller.ProductController;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

	@Mock
	private ProductService productService;
	
	@InjectMocks
	private ProductController productController;
	
	@Autowired
	private MockMvc mockmvc;
	
	@BeforeEach
	
	public void setUp() {
		mockmvc=MockMvcBuilders.standaloneSetup(productController).build();
	}
	
	@Test
	
	public void getProductByCategoryTest() throws JsonProcessingException, Exception {
		String category="Electronics";
		List<ProductDTO> product=List.of(new ProductDTO
				( "Product1", "BrandA", "Description1",  new Price( "USD",50.0), new Inventory(25,15,10), new ArrayList<>()));
		
		when(productService.getProductsByCategory(category, "inventory")).thenReturn(product);
		
	mockmvc.perform(get("/api/products/category/{category}" ,category)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(new ObjectMapper().writeValueAsString(product)))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.message").value("Product Found!!"))
	                .andExpect(jsonPath("$.statusCode").value(200))
	                .andExpect(jsonPath("$.traceId").isNotEmpty());
	           
	
	verify(productService).getProductsByCategory(category, "inventory");
	}
	
	
@Test
	
	public void getProductByNonExistentCategoryTest() throws JsonProcessingException, Exception {
     String category = "toys";  
    
    
    when(productService.getProductsByCategory(category, "inventory")).thenReturn(null);

    
    mockmvc.perform(get("/api/products/category/{category}", category))   
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("No products with sufficient inventory"))
            .andExpect(jsonPath("$.statusCode").value(HttpStatus.NOT_FOUND.value()))   
            .andExpect(jsonPath("$.traceId").isNotEmpty());
	           
	}
}
