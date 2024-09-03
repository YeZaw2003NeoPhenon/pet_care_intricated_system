package com.example.CatholicPetCareSystem.dto;

import lombok.Data;

@Data
public class UserDto {
	
	private String firstName;
	private String lastName;
	private String gender;
	private String email;
	private String password;
	
	private String phoneNumber;
	
	private String userType;
	
	private boolean isEnabled;
	
	private String specialization;
}
