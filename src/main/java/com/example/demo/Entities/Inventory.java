package com.example.demo.Entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Embeddable

public class Inventory {

	@Min(value = 0, message = "Total inventory must be greater than or equal to 0")
	public int total;
	@Min(value = 0, message = "Available inventory must be greater than or equal to 0")
	public int available;
	@Min(value = 0, message = "Reserved inventory must be greater than or equal to 0")
	public int reserved;
	
	
	
	
}
