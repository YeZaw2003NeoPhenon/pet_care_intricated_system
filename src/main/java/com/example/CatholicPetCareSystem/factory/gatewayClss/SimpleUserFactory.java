package com.example.CatholicPetCareSystem.factory.gatewayClss;

import org.springframework.stereotype.Component;

import com.example.CatholicPetCareSystem.entity.user.User;
import com.example.CatholicPetCareSystem.exception.UserAlreadyExistsException;
import com.example.CatholicPetCareSystem.factory.AdminFactory;
import com.example.CatholicPetCareSystem.factory.PatientFactory;
import com.example.CatholicPetCareSystem.factory.UserFactory;
import com.example.CatholicPetCareSystem.factory.VeterinarianFactory;
import com.example.CatholicPetCareSystem.repository.UserRepository;
import com.example.CatholicPetCareSystem.request.RegisterationRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SimpleUserFactory implements UserFactory {
	
	private final UserRepository userRepository;
	
	private final AdminFactory adminFactory;
	private final PatientFactory patientFactory;
	private final VeterinarianFactory veterinarianFactory;

	
	@Override
	public User createUser(RegisterationRequest request) {
		
		if(userRepository.existsByEmail(request.getEmail())) {
			 throw new UserAlreadyExistsException("Christ! you bumped into same user data with " + request.getEmail() + "!");
		}
		
	switch (request.getUserType()) {
		
		case "VET" :
			 return veterinarianFactory.createVeterinarian(request);	

		case "PATIENT":
			return patientFactory.createPatient(request);
			
		case "ADMIN":
		 return adminFactory.createAdmin(request);
		 
		default:
			return null;
		}
	}

}
