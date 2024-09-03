package com.example.CatholicPetCareSystem.entity.user.appointment;

import java.util.List;

import com.example.CatholicPetCareSystem.dto.AppointmentDto;
import com.example.CatholicPetCareSystem.entity.user.appointment.status.AppointmenetStatus;
import com.example.CatholicPetCareSystem.request.AppointmentRequest;

public interface AppointmentService {
	
	public abstract Appointment createAppointment(AppointmentRequest request , Long senderId , Long recipicentId);

	public abstract List<AppointmentDto> getAllAppointments();
	
	public abstract Appointment updateAppointment( Long id , AppointmentRequest request);
	
	public abstract void deleteAppointment(Long id);
	
	public abstract Appointment getAppointmentById( Long id );
	
	public abstract Appointment getAppointmentByNo(String appointmentNo );
	
	public abstract Appointment updateAppointmentStatus(Long appointmentId ,AppointmenetStatus status);
	
}
