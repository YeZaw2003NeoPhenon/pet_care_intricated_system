package com.example.CatholicPetCareSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CatholicPetCareSystem.entity.user.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsByEmail(String email);
	Optional<User>findByEmail(String email);
	Optional<User> findByPhoneNumber(String phoneNumber);
}

