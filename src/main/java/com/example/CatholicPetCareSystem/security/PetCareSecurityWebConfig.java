package com.example.CatholicPetCareSystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.example.CatholicPetCareSystem.security.custom.CustomFilter;
import com.example.CatholicPetCareSystem.security.custom.RateLimitingInterceptor;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class PetCareSecurityWebConfig{

	
	private final PetCareService petCareService;
	
	private final RateLimitingInterceptor rateLimitingInterceptor;
	@Bean
	 DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(petCareService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return	httpSecurity
					.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
					.and()
					.authorizeHttpRequests()
			       .requestMatchers("/admin/**").hasRole("ADMIN")
					.requestMatchers("/vet/**").hasRole("VET")
					.requestMatchers("/patient/**").hasRole("PATIENT")
					.anyRequest().authenticated()
					.and()
					.httpBasic()
					.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
					.addFilterBefore(new CustomFilter(), UsernamePasswordAuthenticationFilter.class)
					.addFilterBefore(rateLimitingInterceptor, UsernamePasswordAuthenticationFilter.class)
					.build();
		
	}
	
}
