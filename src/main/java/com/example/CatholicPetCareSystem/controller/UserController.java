package com.example.CatholicPetCareSystem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.CatholicPetCareSystem.api.ApiPattern;
import com.example.CatholicPetCareSystem.dto.EntityConverter;
import com.example.CatholicPetCareSystem.dto.UserDto;
import com.example.CatholicPetCareSystem.entity.user.User;
import com.example.CatholicPetCareSystem.exception.UserAlreadyExistsException;
import com.example.CatholicPetCareSystem.exception.UserNotFoundException;
import com.example.CatholicPetCareSystem.request.RegisterationRequest;
import com.example.CatholicPetCareSystem.request.UserUpdateRequest;
import com.example.CatholicPetCareSystem.response.ApiResponse;
import com.example.CatholicPetCareSystem.response.FeedbackMessage;
import com.example.CatholicPetCareSystem.service.Iservice.IUserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(ApiPattern.userApi)
@RestController
public class UserController {
	
	private final IUserService iUserService;
	
	private final EntityConverter<User , UserDto> entityConverter;
	
	
		@RequestMapping( value = ApiPattern.registerApi ,method = RequestMethod.POST)
	public ResponseEntity<ApiResponse<UserDto>> registerUser( @RequestBody RegisterationRequest request) {
	 
	 try {
		 User user = iUserService.createUser(request);
		 UserDto  registeredUser  = entityConverter.entityToDto(user, UserDto.class);
		 return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<UserDto>(HttpStatus.OK, FeedbackMessage.user_creation_success_message , registeredUser));
		 
	 }
	 catch(UserAlreadyExistsException exception) {
		 return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<UserDto>(HttpStatus.BAD_REQUEST, exception.getMessage(), null));
	 }
	 catch (Exception e) {
		 return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<UserDto>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
	}
  }
	
	@RequestMapping( value = ApiPattern.updateApi ,method = RequestMethod.PUT)
	public ResponseEntity<ApiResponse<UserDto>> update( @PathVariable(name = "userId") Long id , @RequestBody UserUpdateRequest request ){
		
	try {
		User user = iUserService.updateUser(id, request);
		UserDto updatedUser = entityConverter.entityToDto(user, UserDto.class);
		 return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<UserDto>(HttpStatus.OK, FeedbackMessage.USER_UPDATE_SUCCESS_MESSAGE_STRING , updatedUser));
	}
	catch(UserNotFoundException exception) {
		return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<UserDto>(HttpStatus.BAD_REQUEST, exception.getMessage(), null));
	}
	 catch (Exception e) {
		 return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<UserDto>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
	}
	
	}
	
	
	@RequestMapping( value = ApiPattern.gettAllUsers ,method = RequestMethod.GET)
	public ResponseEntity<ApiResponse<List<UserDto>>> getAll(){
		List<UserDto> users = iUserService.getAllUsers();
		
		  if (users.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.NO_CONTENT)
		                .body(new ApiResponse<>(HttpStatus.NO_CONTENT, "No users found", null));
		    }
		  
		    return ResponseEntity.status(HttpStatus.OK)
		            .body(new ApiResponse<>(HttpStatus.OK, FeedbackMessage.USER_DATAS, users));	
	}
	
	@RequestMapping( value = ApiPattern.deleteUser ,method = RequestMethod.DELETE)
	public ResponseEntity<ApiResponse<UserDto>> delete( @PathVariable(name = "userId") Long id){
		
	try {
		 iUserService.deleteuser(id);
		 return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse<UserDto>(HttpStatus.OK, FeedbackMessage.user_deletion_success_message , null));
	}
	catch(UserNotFoundException exception) {
		return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<UserDto>(HttpStatus.BAD_REQUEST, exception.getMessage(), null));
	}
	 catch (Exception e) {
		 return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<UserDto>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
	}
  }
	
}
