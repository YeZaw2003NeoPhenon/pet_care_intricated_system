package com.example.CatholicPetCareSystem.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.CatholicPetCareSystem.entity.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetCareSecurity implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private List<GrantedAuthority> authorities;
	
	public PetCareSecurity(User user) {
		this.username =	user.getEmail();
		this.password = user.getPassword();
		this.authorities = Collections.unmodifiableList(getImpeccableAuthRoles(user.getUserType()));
		System.out.println(user.getUserType() + "is trying to get over!");
	}

	private List<GrantedAuthority> getImpeccableAuthRoles(String userType) {
		return Arrays.stream(userType.toUpperCase().split(" "))
		.map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
		.map(SimpleGrantedAuthority::new)
		.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	 public boolean isEnabled() {
	        return true;
	 }
	
}
