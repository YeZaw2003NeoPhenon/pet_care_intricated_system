package com.example.CatholicPetCareSystem.dto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class EntityConverter <T , D>{
	
	private final ModelMapper modelMapper;
	
	public D entityToDto(T entity , Class<D> dtoClass) {
		return modelMapper.map(entity, dtoClass);
	}
	
	public T DtoToEntity(D dto , Class<T> entityClass) {
		return modelMapper.map(dto, entityClass);
	}
	
}
