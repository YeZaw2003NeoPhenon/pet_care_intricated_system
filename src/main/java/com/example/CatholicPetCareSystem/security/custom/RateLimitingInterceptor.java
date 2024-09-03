package com.example.CatholicPetCareSystem.security.custom;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.CatholicPetCareSystem.security.request.requestInfo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitingInterceptor extends OncePerRequestFilter {
	
	private ConcurrentHashMap<String, requestInfo> requestMap = new ConcurrentHashMap<>();
	
	private static final long TIME_WINDOW = 60000;
    private static final int MAX_REQUESTS = 10;
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	   	String clientIp = request.getRemoteAddr();
    	
	     requestInfo requestInfo = requestMap.get(clientIp);
	     
	     if( requestInfo == null ) {
	    	 requestInfo = new requestInfo(System.currentTimeMillis() , 1);
	    	 requestMap.put(clientIp, requestInfo);
	     }
	     else {
	    	 long currentTime = System.currentTimeMillis();
	    	 
	    	 if( currentTime - requestInfo.getTimestamp() > TIME_WINDOW ) {
	    		 requestInfo.setTimestamp(currentTime);
	    		 requestInfo.setCount(1);
	    	 }
	    	 else {
	    		 if( requestInfo.getCount() > MAX_REQUESTS ) {
	    			 response.setStatus(429); 
	    			 response.getWriter().write("Too many requests, please try again later.");
	    			 return;
	    		 }
	    		 requestInfo.incrementCount();
	    	 }
	     }
	     filterChain.doFilter(request, response);
		
	}
}
