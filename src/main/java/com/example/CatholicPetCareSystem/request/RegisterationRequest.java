package com.example.CatholicPetCareSystem.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterationRequest {

	private Long id;
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
