package com.example.demo.Entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductDTO {

	

	@NotBlank(message = "Product name is mandatory")
	private String name;

	@NotBlank(message = "Brand is mandatory")
	private String brand;

	@NotBlank(message = "Description is mandatory")
	private String description;

	@NotNull(message = "Price cannot be null")
	@Min(value = 0, message = "Price must be positive")
	private Price price;

	@NotNull(message = "Inventory cannot be null")
	@Min(value = 0, message = "Inventory must be non-negative")
	private Inventory inventory;

	private List<attributes> attributes;
	
	

}
