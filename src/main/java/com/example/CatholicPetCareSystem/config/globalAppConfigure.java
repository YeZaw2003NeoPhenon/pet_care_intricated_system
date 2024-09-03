package com.example.CatholicPetCareSystem.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class globalAppConfigure {
	
	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}
	
}
