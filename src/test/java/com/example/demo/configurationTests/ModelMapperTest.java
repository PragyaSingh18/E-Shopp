package com.example.demo.configurationTests;



import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.example.demo.configuration.ModelMapperConfig;

public class ModelMapperTest {
	
	
	@InjectMocks
    private ModelMapperConfig modelMapperConfig;
	
	@BeforeEach
	public void set() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	
	public void ModelMapperConfigTest() {
		ModelMapper mapper= modelMapperConfig.modelMapperbean();
		assertThat(mapper).isNotNull();
		
	}
	

}
