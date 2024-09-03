package com.example.CatholicPetCareSystem.entity.user;

import java.util.List;

import com.example.CatholicPetCareSystem.entity.user.appointment.Appointment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "super_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String firstName;
	private String lastName;
	private String gender;
	private String email;
	private String password;
	
	@Column(name = "mobile")
	private String phoneNumber;
	
	private String userType;
	
	private boolean isEnabled;
	
	@Transient
	private String specialization;
	
	@Transient
	private List<Appointment> appointments;
	
}
