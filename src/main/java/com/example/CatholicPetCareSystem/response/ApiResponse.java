package com.example.CatholicPetCareSystem.response;




import org.springframework.http.HttpStatus;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T>{
	
	private HttpStatus status;
	private String message;
	
	private T data;
		
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
