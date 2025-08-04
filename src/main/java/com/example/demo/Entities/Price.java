package com.example.demo.Entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Price {

	@NotNull(message = "Currency cannot be null")
	private String currency;
	@Min(value = 0, message = "Price must be greater than or equal to 0")
	private double amount;
	
	
	
}
