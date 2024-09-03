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
@Table(name = "Veterinarian")
@PrimaryKeyJoinColumn(name = "vet_id")
public class Veterinarian extends User{
	
	private Long id;
	
	private String specialization;
}
