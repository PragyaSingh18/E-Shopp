package com.example.demo.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomErrorHandler extends ResponseEntityExceptionHandler  {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> customHandler(Exception ex, WebRequest request) throws Exception{
		
		ErrorDetails err=new ErrorDetails(LocalDateTime.now(),ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(err, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> customHandlerResourceNotFoundException(Exception ex, WebRequest request) throws Exception{
		
		ErrorDetails err=new ErrorDetails(LocalDateTime.now(),ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(err, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<ErrorDetails> customHandlerCategoryNotFoundException(Exception ex, WebRequest request) throws Exception{
		
		ErrorDetails err=new ErrorDetails(LocalDateTime.now(),ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(err, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(ProductNotAvailableException.class)
	public ResponseEntity<ErrorDetails> customHandlerProductNotAvailableException(Exception ex, WebRequest request) throws Exception{
		
		ErrorDetails err=new ErrorDetails(LocalDateTime.now(),ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(err, HttpStatus.NOT_FOUND);
		
	}
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
		ErrorDetails err= new ErrorDetails(LocalDateTime.now(), ex.getFieldError().getDefaultMessage(), request.getDescription(false));
		return new ResponseEntity(err, HttpStatus.BAD_REQUEST);
	}
	
	
}
