package com.example.demo.ExceptionTests;

import static org.junit.Assert.assertEquals;


import org.junit.jupiter.api.Test;

import com.example.demo.exception.ProductNotAvailableException;



public class ProductNotAvailableExceptionTest {
	
	@Test
	public void ProductNotAvailableExceptionMessage() {
	
	String expectedmsg="This is ProductNotAvailableException";
	
	ProductNotAvailableException msg= new ProductNotAvailableException(expectedmsg);
	assertEquals(expectedmsg, msg.getMessage());
	}

}
