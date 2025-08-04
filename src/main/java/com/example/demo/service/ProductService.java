package com.example.demo.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Entities.Product;
import com.example.demo.Entities.ProductDTO;
import com.example.demo.UserRepo.Repo;
import com.example.demo.configuration.ModelMapperConfig;
import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.exception.ProductNotAvailableException;


@Service
public class ProductService {

	@Autowired
	private Repo repo;
	
	@Autowired
	private ModelMapperConfig modelMapper;
	
	public List<Product> findByCategory(String category) {
        return repo.findAll().stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
	
   
	public List<ProductDTO> getProductsByCategory(String category, String sortBy) throws ProductNotAvailableException {
        List<Product> products = findByCategory(category);

      if (products.isEmpty()) {
            throw new CategoryNotFoundException("Category " + category + " does not exist.");
        }

       List<Product> filteredProducts = products.stream()
                .filter(p -> p.getInventory().getAvailable() > 0) 
                .collect(Collectors.toList());

       if (filteredProducts.isEmpty()) {
            throw new ProductNotAvailableException("No products available with sufficient inventory.");
        }

       if ("price".equalsIgnoreCase(sortBy)) {
            filteredProducts.sort(Comparator.comparing(p -> p.getPrice().getAmount()));
        } else if ("inventory".equalsIgnoreCase(sortBy)) {
            filteredProducts.sort(Comparator.comparing(p -> p.getInventory().getAvailable()));
        }

       return filteredProducts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

   private ProductDTO convertToDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
}