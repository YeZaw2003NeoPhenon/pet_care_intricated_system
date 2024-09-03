package com.example.CatholicPetCareSystem.request;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.CatholicPetCareSystem.entity.user.appointment.status.AppointmenetStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {
	
	@NotNull(message = "Reason cannot be null")
    @Size(min = 5, max = 255, message = "Reason must be between 5 and 255 characters")
    private String reason;
	
    @NotNull(message = "Appointment number cannot be null")
    private LocalDate date;
    
    @NotNull(message = "Date cannot be null")
    private LocalTime time;
    
    @NotNull(message = "Time cannot be null")
    private AppointmenetStatus status;
    
    @NotNull(message = "Patient ID cannot be null")
    private Long senderId;
    
    @NotNull(message = "Veterinarian ID cannot be null")
    private Long recipientId;
    
   
}
