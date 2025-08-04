package com.example.demo.ServiceTests;



import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.Entities.Inventory;
import com.example.demo.Entities.Price;
import com.example.demo.Entities.Product;
import com.example.demo.Entities.ProductDTO;
import com.example.demo.UserRepo.Repo;
import com.example.demo.configuration.ModelMapperConfig;
import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.exception.ProductNotAvailableException;
import com.example.demo.service.ProductService;




public class ProductServiceTest {
	
	@Mock
	private Repo repo;
	
	@Mock
    private ModelMapperConfig modelMapper;
	
	@InjectMocks
	private ProductService productService;
	
	@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	@Test
	public void findByCategoryWithMatchingCatrgoryTests() {
       
        List<Product> li= Arrays.asList(new Product(1, "Product1", "BrandA", "Description1", 
        		new Price("USD", 100.0), new Inventory(30,25,5), new ArrayList<>(), "electronics"));
       
       when(repo.findAll()).thenReturn(li);
       List<Product> result= productService.findByCategory("electronics");
      // assertEquals(result,li);
       assertTrue(result.stream().allMatch(p->p.getCategory().equalsIgnoreCase("electronics")));
       
       }
	
	@Test
	public void findByCategoryWithNonMatchingCategoryTests() {
       
        List<Product> li= Arrays.asList(new Product(1, "Product1", "BrandA", "Description1", 
        		new Price("USD", 100.0), new Inventory(30,25,5), new ArrayList<>(), "electronics"));
       
       when(repo.findAll()).thenReturn(li);
       List<Product> result= productService.findByCategory("toys");
      
       assertTrue(result.isEmpty());
       
       }
	
	@Test
    void testGetProductsByCategoryWithValidCategoryAndInventorySortedByPrice() throws Exception {
        
        List<Product> products = Arrays.asList(
            new Product(1, "Product1", "BrandA", "Description1", new Price( "USD",50.0), new Inventory(25,15,10), new ArrayList<>(), "Electronics"),
            
            new Product(2, "Product3", "BrandC", "Description3", new Price("USD",100.0), new Inventory(40,30,15), new ArrayList<>(), "Electronics")
        );

        when(repo.findAll()).thenReturn(products);

        ProductDTO product1DTO = new ProductDTO( "Product1", "BrandA", "Description1",  new Price( "USD",50.0), new Inventory(25,15,10), new ArrayList<>());
        ProductDTO product2DTO = new ProductDTO( "Product2", "BrandB", "Description2",  new Price( "USD",100.0), new Inventory(40,30,15), new ArrayList<>());
        

        when(modelMapper.map(products.get(0), ProductDTO.class)).thenReturn(product1DTO);
        when(modelMapper.map(products.get(1), ProductDTO.class)).thenReturn(product2DTO);
       

       
        List<ProductDTO> result = productService.getProductsByCategory("Electronics", "price");

        
        assertEquals(2, result.size());
        assertEquals(50.0, result.get(0).getPrice().getAmount(),0.001);
        assertEquals(100.0, result.get(1).getPrice().getAmount(),0.001);
       
    }

    @Test
    void testGetProductsByCategoryWithNonExistentCategory() {
       
        when(repo.findAll()).thenReturn(Collections.emptyList());

      
        assertThrows(CategoryNotFoundException.class, () -> {
            productService.getProductsByCategory("NonExistentCategory", "price");
        });
    }

    @Test
    void testGetProductsByCategoryNoAvailableInventory() {
        
        List<Product> products = Arrays.asList(
            new Product(1, "Product1", "BrandA", "Description1", new Price("USD", 100.0), new Inventory(0,0,0), new ArrayList<>(), "Electronics"),
            new Product(2, "Product2", "BrandB", "Description2", new Price("USD", 200.0), new Inventory(0,0,0), new ArrayList<>(), "Electronics")
        );

        when(repo.findAll()).thenReturn(products);

        
        assertThrows(ProductNotAvailableException.class, () -> {
        	productService.getProductsByCategory("Electronics", "price");
        });
    }

    @Test
    void testGetProductsByCategoryValidCategoryAndPriceSortedByInventory() throws Exception {
       
        List<Product> products = Arrays.asList(
            new Product(1, "Product1", "BrandA", "Description1", new Price("USD", 100.0), new Inventory(25,15,5), new ArrayList<>(), "Electronics"),
            new Product(2, "Product2", "BrandB", "Description2", new Price("USD", 200.0), new Inventory(40,30,15), new ArrayList<>(), "Electronics")
        );

        when(repo.findAll()).thenReturn(products);

        ProductDTO product1DTO = new ProductDTO("Product1", "BrandA", "Description1", new Price("USD", 50.0), new Inventory(25, 15, 10), new ArrayList<>());
        ProductDTO product2DTO = new ProductDTO("Product1", "BrandA", "Description1", new Price("USD", 100.0), new Inventory(40,30,15), new ArrayList<>());
        when(modelMapper.map(products.get(0), ProductDTO.class)).thenReturn(product1DTO);
        when(modelMapper.map(products.get(1), ProductDTO.class)).thenReturn(product2DTO);

       
        List<ProductDTO> result = productService.getProductsByCategory("Electronics", "inventory");

        
        assertEquals(2, result.size());
        assertEquals(15, result.get(0).getInventory().getAvailable());
        assertEquals(30, result.get(1).getInventory().getAvailable());
    }
}
