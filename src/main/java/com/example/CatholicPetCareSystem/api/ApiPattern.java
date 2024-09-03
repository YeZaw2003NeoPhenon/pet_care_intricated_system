package com.example.CatholicPetCareSystem.api;


public class ApiPattern {
	public static final String userApi = "/api/v1/users";
	
	public static final String registerApi = "/register";
	
	public static final String updateApi = "/update/{userId}";
	
	public static final String gettAllUsers = "/all-users";
	
	public static final String deleteUser = "/delete/{userId}"; 
	
	public static final String findUserById = "/{userId}";
	
	
	public static final String appointment_api = "/api/v1/appointments";
	
	public static final String appointment_registeration_api = "/createApp";
	
	public static final String appointment_update_api = "/update/{appId}";
	
	public static final String getAllAppointments = "/all-apps";
	
	public static final String getAppointmentById_api = "/select/{appId}";
	
	public static final String deleteAppointmentApi = "/delete/{appId}";
	
	public static final String getAppointmentByNo = "/{appNo}";
	
	public static final String updateAppStatusApi = "/{appId}/status";
	

}
