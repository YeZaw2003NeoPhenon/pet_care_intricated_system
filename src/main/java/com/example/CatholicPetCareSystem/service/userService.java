package com.example.CatholicPetCareSystem.service;

import java.util.List;

import com.example.CatholicPetCareSystem.dto.UserDto;
import com.example.CatholicPetCareSystem.entity.user.User;
import com.example.CatholicPetCareSystem.request.RegisterationRequest;
import com.example.CatholicPetCareSystem.request.UserUpdateRequest;

public interface userService {
	public abstract User createUser(RegisterationRequest registerationRequest);
	
	public abstract User updateUser(Long userId , UserUpdateRequest request);
	
	public abstract User findUserById(Long userId);
	
	public abstract List<UserDto> getAllUsers();
	
	public abstract void deleteuser(Long userId);
}
