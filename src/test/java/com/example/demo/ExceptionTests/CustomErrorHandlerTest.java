package com.example.demo.ExceptionTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.exception.CustomErrorHandler;
import com.example.demo.exception.ErrorDetails;
import com.example.demo.exception.ProductNotAvailableException;
import com.example.demo.exception.ResourceNotFoundException;

public class CustomErrorHandlerTest {


	private CustomErrorHandler customErrorHandler;
	
	@BeforeEach
	public void set() {
		
		customErrorHandler = new CustomErrorHandler();
	}
	
	@Test
	
	public void customHandler() throws Exception {
		Exception exception = new Exception("Test Exception");
        WebRequest request = mock(ServletWebRequest.class);
        
       when(request.getDescription(false)).thenReturn("Test Description");
       ResponseEntity<ErrorDetails> result = customErrorHandler.customHandler(exception, request);
        
        assertNotNull(result); 
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        
        ErrorDetails err = result.getBody();
        assertNotNull(err);
        assertEquals("Test Exception", err.getMessage());
        assertEquals("Test Description", err.getDesc()); 
		
	}
	
@Test
	
	public void customHandlerResourceNotFoundException() throws Exception {
	ResourceNotFoundException exception = new ResourceNotFoundException("Test ResourceNotFoundException");
        WebRequest request = mock(ServletWebRequest.class);
        
       when(request.getDescription(false)).thenReturn("Test Description");
       ResponseEntity<ErrorDetails> result = customErrorHandler.customHandlerResourceNotFoundException(exception, request);
        
        assertNotNull(result); 
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        
        ErrorDetails err = result.getBody();
        assertNotNull(err);
        assertEquals("Test ResourceNotFoundException", err.getMessage());
        assertEquals("Test Description", err.getDesc()); 
		
	}

@Test

public void customHandlerCategoryNotFoundException() throws Exception {
	CategoryNotFoundException exception = new CategoryNotFoundException("Test CategoryNotFoundException");
    WebRequest request = mock(ServletWebRequest.class);
    
   when(request.getDescription(false)).thenReturn("Test Description");
   ResponseEntity<ErrorDetails> result = customErrorHandler.customHandlerCategoryNotFoundException(exception, request);
    
    assertNotNull(result); 
    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    
    ErrorDetails err = result.getBody();
    assertNotNull(err);
    assertEquals("Test CategoryNotFoundException", err.getMessage());
    assertEquals("Test Description", err.getDesc()); 
	
}

@Test

public void customHandlerProductNotAvailableException() throws Exception {
	ProductNotAvailableException exception = new ProductNotAvailableException("Test ProductNotAvailableException");
    WebRequest request = mock(ServletWebRequest.class);
    
   when(request.getDescription(false)).thenReturn("Test Description");
   ResponseEntity<ErrorDetails> result = customErrorHandler.customHandlerProductNotAvailableException(exception, request);
    
    assertNotNull(result); 
    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    
    ErrorDetails err = result.getBody();
    assertNotNull(err);
    assertEquals("Test ProductNotAvailableException", err.getMessage());
    assertEquals("Test Description", err.getDesc()); 
	
}

@Test

public void handleMethodArgumentNotValid() throws Exception {
	MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
	 FieldError fieldError = new FieldError("objectName", "fieldName", "Field is invalid");
	 when(exception.getFieldError()).thenReturn(fieldError);
    WebRequest request = mock(ServletWebRequest.class);
    
   when(request.getDescription(false)).thenReturn("Test Description");
   ResponseEntity<Object> result = customErrorHandler.handleMethodArgumentNotValid(exception,null,HttpStatus.BAD_REQUEST, request);
    
    assertNotNull(result); 
    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    
    ErrorDetails err = (ErrorDetails) result.getBody();
    assertNotNull(err);
    assertEquals("Field is invalid", err.getMessage());
    assertEquals("Test Description", err.getDesc()); 
	
}
	
}
