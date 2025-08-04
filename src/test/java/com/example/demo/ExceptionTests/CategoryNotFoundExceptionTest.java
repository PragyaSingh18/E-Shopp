package com.example.demo.ExceptionTests;

import static org.junit.Assert.assertEquals;


import org.junit.jupiter.api.Test;

import com.example.demo.exception.CategoryNotFoundException;



public class CategoryNotFoundExceptionTest {
	
	@Test
	public void CategoryNotFoundExceptionMessage() {
	String expectedmsg="This is CategoryNotFoundExceptionTest ";
	
	CategoryNotFoundException msg= new CategoryNotFoundException(expectedmsg);
	assertEquals(expectedmsg, msg.getMessage());
	}
}
