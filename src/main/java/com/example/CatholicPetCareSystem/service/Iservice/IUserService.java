package com.example.CatholicPetCareSystem.service.Iservice;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.CatholicPetCareSystem.dto.EntityConverter;
import com.example.CatholicPetCareSystem.dto.UserDto;
import com.example.CatholicPetCareSystem.entity.user.User;
import com.example.CatholicPetCareSystem.exception.UserAlreadyExistsException;
import com.example.CatholicPetCareSystem.exception.UserNotFoundException;
import com.example.CatholicPetCareSystem.factory.UserFactory;
import com.example.CatholicPetCareSystem.repository.UserRepository;
import com.example.CatholicPetCareSystem.request.RegisterationRequest;
import com.example.CatholicPetCareSystem.request.UserUpdateRequest;
import com.example.CatholicPetCareSystem.service.userService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IUserService implements userService{
	
	private final UserRepository userRepository;
	
	private final UserFactory userFactory;
	
	private final EntityConverter<User, UserDto> entityConverter;
	
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public User createUser(RegisterationRequest registerationRequest) {
		
		if(userRepository.existsByEmail(registerationRequest.getEmail())) {
			throw new UserAlreadyExistsException("User Already Exists With" + registerationRequest.getEmail());
		}
		registerationRequest.setPassword(passwordEncoder.encode(registerationRequest.getPassword()));
		
	 return userFactory.createUser(registerationRequest);
		
	}

	@Override
	public User updateUser(Long userId, UserUpdateRequest request) {
		
		User existingUser = findUserById(userId);
		
		  Map<String, Runnable> fieldUpdaters = Map.of(
			        "firstName", () -> Optional.ofNullable(request.getFirstName())
			                                   .ifPresent(existingUser::setFirstName),
			        "lastName", () -> Optional.ofNullable(request.getLastName())
			                                  .ifPresent(existingUser::setLastName),
			        "email", () -> Optional.ofNullable(request.getEmail())
			                               .ifPresent(email -> {
//			                                   if (userRepository.existsByEmail(email)) {
//			                                       throw new UserAlreadyExistsException("Email " + email + " is already in use.");
//			                                   }
			                                   existingUser.setEmail(email);
			                               }),
			        "phoneNumber", () -> Optional.ofNullable(request.getPhoneNumber())
			                                .ifPresent(existingUser::setPhoneNumber),
			        "specialization", () -> Optional.ofNullable(request.getSpecialization())
			                                  .ifPresent(existingUser::setSpecialization)
			    );
		  
		  fieldUpdaters.values().forEach(Runnable::run);
		  
		  return userRepository.save(existingUser);
	}
	
	@Override
	public User findUserById(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(" User not trackable!"));
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users
				.stream()
				.sorted(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName))
				.map(user -> entityConverter.entityToDto(user, UserDto.class)).collect(Collectors.toList());
	}

	@Override
	public void deleteuser(Long userId) {
		userRepository.findById(userId).ifPresentOrElse( userRepository::delete , () -> {
			 throw new UserNotFoundException("Failed to bail out user to delete!");
		});
	}
	
	
}
