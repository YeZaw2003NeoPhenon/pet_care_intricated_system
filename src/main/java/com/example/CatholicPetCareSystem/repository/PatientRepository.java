package com.example.CatholicPetCareSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CatholicPetCareSystem.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{

}
