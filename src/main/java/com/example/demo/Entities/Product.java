package com.example.demo.Entities;

import java.util.ArrayList;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Entity
@Table(name = "PRODUCT")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotBlank(message = "Product name is mandatory")
	private String name;
	@NotBlank(message = "Brand is mandatory")
	private String brand;
	@NotBlank(message = "Description is mandatory")
	private String description;

	@Valid
	@Embedded
	@NotNull(message = "Price cannot be null")

	private Price price;

	@Valid
	@Embedded
	@NotNull(message = "Inventory cannot be null")
	
	private Inventory inventory;

	@ElementCollection
	@CollectionTable(name = "product_attributes", joinColumns = @JoinColumn(name = "product_id"))
	private List<attributes> attributes = new ArrayList<>();
    
	private String category;
	

}
