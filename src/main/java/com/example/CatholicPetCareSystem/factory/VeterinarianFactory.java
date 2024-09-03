package com.example.CatholicPetCareSystem.factory;

import org.springframework.stereotype.Service;

import com.example.CatholicPetCareSystem.entity.Veterinarian;
import com.example.CatholicPetCareSystem.entity.user.User;
import com.example.CatholicPetCareSystem.mapper.userDataMapper;
import com.example.CatholicPetCareSystem.repository.VeterinarianRepository;
import com.example.CatholicPetCareSystem.request.RegisterationRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VeterinarianFactory {

	private final VeterinarianRepository veterinarianRepository;
	
	private final userDataMapper mapper;
	
	public User createVeterinarian(RegisterationRequest request) {
		Veterinarian veter = new Veterinarian();
		mapper.setCommonAttributes(request,veter);
		veter.setSpecialization(request.getSpecialization());
		return veterinarianRepository.save(veter);		
	}
	
}
