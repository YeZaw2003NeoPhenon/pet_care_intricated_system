package com.example.CatholicPetCareSystem.entity.user.appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CatholicPetCareSystem.entity.user.User;

@Repository
public interface AppointmenetRepository extends JpaRepository<Appointment, Long> {
	Optional<Appointment> findByAppointmentNo(String appointmentNo);
    Optional<Appointment> findByPatientAndVeterinarianAndDateAndTime(User patient, User veterinarian, LocalDate date, LocalTime time);
}
