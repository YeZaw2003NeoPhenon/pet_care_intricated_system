package com.example.CatholicPetCareSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CatholicPetCareSystem.entity.Veterinarian;

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {

}
