package com.example.CatholicPetCareSystem.factory;
import org.springframework.stereotype.Service;

import com.example.CatholicPetCareSystem.entity.Admin;
import com.example.CatholicPetCareSystem.entity.user.User;
import com.example.CatholicPetCareSystem.mapper.userDataMapper;
import com.example.CatholicPetCareSystem.repository.AdminRepository;
import com.example.CatholicPetCareSystem.request.RegisterationRequest;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AdminFactory {
	
	private final AdminRepository adminRepository;
	private final userDataMapper dataMapper;
	
	public User createAdmin(RegisterationRequest request) {
		Admin admin = new Admin();
		dataMapper.setCommonAttributes(request, admin);
		return adminRepository.save(admin);
	}
	
}
