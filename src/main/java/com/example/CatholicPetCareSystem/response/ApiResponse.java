package com.example.CatholicPetCareSystem.response;



import java.util.List;

import org.springframework.http.HttpStatus;

import com.example.CatholicPetCareSystem.dto.UserDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T>{
	
	private HttpStatus status;
	private String message;
	
	private T data;
	
	private List<UserDto> users;
	
    public ApiResponse(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

//	public ApiResponse(HttpStatus status, String message, List<UserDto> users) {
//		this.status = status;
//		this.message = message;
//		this.users = users;
//	}

	
}
