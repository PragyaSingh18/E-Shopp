package com.example.demo.apiResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse {
	    private String message;
	    private Object data;         
	    private int statusCode;      
	    private String traceId; 
	    
	    
	    
		
	    
}
