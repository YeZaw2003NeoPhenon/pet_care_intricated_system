package com.example.CatholicPetCareSystem.entity;

import com.example.CatholicPetCareSystem.entity.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Patient")
@PrimaryKeyJoinColumn(name = "patient_id")
public class Patient extends User{
	
		private Long id;
	
}
