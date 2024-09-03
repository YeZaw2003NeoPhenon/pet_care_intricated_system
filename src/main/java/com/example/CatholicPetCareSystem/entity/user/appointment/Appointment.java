package com.example.CatholicPetCareSystem.entity.user.appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.example.CatholicPetCareSystem.entity.user.User;
import com.example.CatholicPetCareSystem.entity.user.appointment.status.AppointmenetStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Appointment")
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String reason;
	private String appointmentNo;
	private LocalDate date;
	private LocalTime time;
	
	@CreationTimestamp
	private LocalDate createdAt;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender")
	private User patient;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipient")
	private User veterinarian;
	
	@Enumerated(EnumType.STRING)
	private AppointmenetStatus status;
	
	public void addPatient( User sender ) {
		this.setPatient(sender);
		
		if( sender.getAppointments() == null ) {
			sender.setAppointments(new ArrayList<>());
		}
		sender.getAppointments().add(this);
	}
	
	public void addVeterinarian( User recipient ) {
		this.setVeterinarian(recipient);
		
		if( recipient.getAppointments() == null ) {
			recipient.setAppointments(new ArrayList<>());
		}
		recipient.getAppointments().add(this);
	}
	
	public void setAppointmentNumber() {
//		this.appointmentNo = String.valueOf(new Random().nextLong()).substring(1 , 11 );
		this.appointmentNo = UUID.randomUUID().toString().replaceAll("-", "").substring(0 ,10).toUpperCase();
	}
	
}