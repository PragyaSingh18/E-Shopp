package com.example.demo.ExceptionTests;

import static org.junit.Assert.assertEquals;


import org.junit.jupiter.api.Test;

import com.example.demo.exception.ResourceNotFoundException;



public class ResourceNotFoundExceptionTest {
	
	@Test
	
	public void ResourceNotFoundExceptionMessage() {
		String expectedmsg="This is ResourceNotFoundException";
		
		ResourceNotFoundException msg= new ResourceNotFoundException(expectedmsg);
		assertEquals(expectedmsg, msg.getMessage());
	}

}
