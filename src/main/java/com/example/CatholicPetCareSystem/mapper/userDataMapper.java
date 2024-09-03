package com.example.CatholicPetCareSystem.mapper;

import org.springframework.stereotype.Component;

import com.example.CatholicPetCareSystem.entity.user.User;
import com.example.CatholicPetCareSystem.request.RegisterationRequest;

@Component
public class userDataMapper {
	
	public void setCommonAttributes(RegisterationRequest request, User target) {
		target.setFirstName(request.getFirstName());
		target.setLastName(request.getLastName());
		target.setEmail(request.getEmail());
		target.setPassword(request.getPassword());
		target.setGender(request.getGender());
		target.setUserType(request.getUserType());
		target.setPhoneNumber(request.getPhoneNumber());
		target.setEnabled(true);
	}
}
