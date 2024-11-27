package com.example.CatholicPetCareSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.CatholicPetCareSystem.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsByEmail(String email);
	Optional<User>findByEmail(String email);
	Optional<User> findByPhoneNumber(String phoneNumber);
	
	@Query("SELECT u FROM User u INNER JOIN Appointment a ON u.id = a.patient.id WHERE appointmentNo = :appointmentNo")
	Optional<User> findByAppointmentNo(@Param("appointmentNo") String appointmentNo);
}

