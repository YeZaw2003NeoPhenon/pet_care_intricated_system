package com.example.CatholicPetCareSystem.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.CatholicPetCareSystem.entity.user.appointment.status.AppointmenetStatus;

import lombok.Data;

@Data
public class AppointmentDto {
	
	private String reason;
	private String appointmentNo;
	private LocalDate date;
	private LocalTime time;
	
	private LocalDate createdAt;
	
	private UserDto patient; // in here if we amorphiously knuckle down on User Entity , we would lead to unserilization problems... 
	
	private UserDto veterinarian;
	
	private AppointmenetStatus status;
}
