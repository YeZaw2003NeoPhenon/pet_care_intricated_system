package com.example.CatholicPetCareSystem.security.custom;

import org.slf4j.LoggerFactory;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;

import org.slf4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class CustomFilter extends UsernamePasswordAuthenticationFilter {
	
	private final Logger logger = LoggerFactory.getLogger(CustomFilter.class);
	
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
       String clientIp = request.getRemoteAddr();
       
       System.out.println("Authentication attempt: User: " + username + ", IP: " + clientIp);
       
        logger.info("Attempting authentication for user: " + username);
        
        return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
    
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
       
        String clientIp = request.getRemoteAddr();
        System.out.println("Success: User: " + authResult.getName() + ", IP: " + clientIp);

        
        logger.info("Authentication successful for user: " + authResult.getName());
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
      
        String clientIp = request.getRemoteAddr();
        System.out.println("Failure: IP: " + clientIp + ", Reason: " + failed.getMessage());

        logger.info("Authentication failed: " + failed.getMessage());
        
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
