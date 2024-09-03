package com.example.CatholicPetCareSystem.factory;

import org.springframework.stereotype.Service;

import com.example.CatholicPetCareSystem.entity.Patient;
import com.example.CatholicPetCareSystem.entity.user.User;
import com.example.CatholicPetCareSystem.mapper.userDataMapper;
import com.example.CatholicPetCareSystem.repository.PatientRepository;
import com.example.CatholicPetCareSystem.request.RegisterationRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientFactory {
	
	private final PatientRepository patientRepository;
	private final userDataMapper dataMapper;
	
	public User createPatient(RegisterationRequest request) {
			Patient patient = new Patient();
			dataMapper.setCommonAttributes(request, patient);
			return patientRepository.save(patient);
		}
}
