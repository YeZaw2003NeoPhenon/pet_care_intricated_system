package com.example.CatholicPetCareSystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class PetCareSecurityWebConfig{

	private String[] public_appo_auth_filters = {"/api/v1/appointments/all-apps","/api/v1/appointments/select/{appId}","/api/v1/appointments/createApp"};
	
	private String[] public_user_filters = {"/api/v1/users/all-users","/api/v1/appointments/delete/{appId}","/api/v1/users/register"};
	
	private final PetCareService petCareService;
	
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
					.csrf().disable()
					.authorizeHttpRequests()
					.requestMatchers(public_appo_auth_filters).hasAnyRole("PATIENT","VET","ADMIN")
					.requestMatchers(public_user_filters).hasAnyRole("ADMIN","PATIENT","VET")
					.anyRequest().authenticated()
					.and()
					.httpBasic()
					.and()
					.logout()
					.logoutUrl("/logout")
					.clearAuthentication(true)
					.invalidateHttpSession(true)
					.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
					.build();
	}
	
}
