package com.example.CatholicPetCareSystem.factory;

import com.example.CatholicPetCareSystem.entity.user.User;
import com.example.CatholicPetCareSystem.request.RegisterationRequest;

public interface UserFactory {
	public abstract User createUser(RegisterationRequest request );
}
