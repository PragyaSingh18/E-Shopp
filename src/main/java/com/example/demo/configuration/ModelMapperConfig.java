package com.example.demo.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig extends ModelMapper{
	
	@Bean
	public ModelMapper modelMapperbean() {
	        return new ModelMapper();
	    }
	
}
