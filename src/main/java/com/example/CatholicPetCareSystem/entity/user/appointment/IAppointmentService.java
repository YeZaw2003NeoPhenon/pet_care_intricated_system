package com.example.CatholicPetCareSystem.entity.user.appointment;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.example.CatholicPetCareSystem.dto.AppointmentDto;
import com.example.CatholicPetCareSystem.dto.EntityConverter;
import com.example.CatholicPetCareSystem.entity.user.User;
import com.example.CatholicPetCareSystem.entity.user.appointment.status.AppointmenetStatus;
import com.example.CatholicPetCareSystem.entity.user.appointment.statusflow.AppointmentStatusFlow;
import com.example.CatholicPetCareSystem.exception.AppointmentAlreadyExistsException;
import com.example.CatholicPetCareSystem.exception.AppointmentsNotFoundException;
import com.example.CatholicPetCareSystem.exception.InvalidStatusTransitionException;
import com.example.CatholicPetCareSystem.exception.UserNotFoundException;
import com.example.CatholicPetCareSystem.repository.UserRepository;
import com.example.CatholicPetCareSystem.request.AppointmentRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IAppointmentService implements AppointmentService{
	
	private final UserRepository userRepository;
	
	private final AppointmenetRepository appointmenetRepository;
	
	private final EntityConverter<Appointment, AppointmentDto> entityConverter;

	@Override
	public Appointment createAppointment( AppointmentRequest request , Long senderId, Long recipicentId) {
		
	User sender = userRepository.findById(senderId).orElseThrow(() -> new UserNotFoundException("patient is not entirely in progress with " + senderId));
	
	User recipient = userRepository.findById(recipicentId).orElseThrow(() -> new UserNotFoundException("cat doctor is not entirely in progress with " + recipicentId));

    Optional<Appointment> existingAppointment = appointmenetRepository.findByPatientAndVeterinarianAndDateAndTime(
    		sender, recipient, request.getDate(), request.getTime());

    if (existingAppointment.isPresent()) {
        throw new AppointmentAlreadyExistsException("An appointment already exists for this patient and veterinarian at the specified date and time.");
    }
    
	Appointment appointment = new Appointment();
	
    appointment.setReason(request.getReason());
    appointment.setDate(request.getDate());
    appointment.setTime(request.getTime());
    appointment.setPatient(sender);
    appointment.setVeterinarian(recipient);
    appointment.setAppointmentNumber();
    appointment.setStatus(AppointmenetStatus.WAITING_FOR_APPROVAL);
    
		return appointmenetRepository.save(appointment);
	}
	


	@Override
	public Appointment updateAppointment(Long id, AppointmentRequest request) {
        Appointment existingAppointment = appointmenetRepository.findById(id)
                .orElseThrow(() -> new AppointmentsNotFoundException("Appointment not found with ID: " + id));

	    Optional.ofNullable(request.getReason()).ifPresent(existingAppointment::setReason);
	    Optional.ofNullable(request.getDate()).ifPresent(existingAppointment::setDate);
	    Optional.ofNullable(request.getTime()).ifPresent(existingAppointment::setTime);
	    Optional.ofNullable(request.getStatus()).ifPresent(existingAppointment::setStatus);


//	    if (request.getSenderId() != null) {
//	        User patient = userRepository.findById(request.getSenderId())
//	            .orElseThrow(() -> new UserNotFoundException("Patient not found with ID: " + request.getSenderId()));
//	        existingAppointment.setPatient(patient);
//	    }
//	    
//	    
//	    if (request.getRecipientId() != null) {
//	        User veterinarian = userRepository.findById(request.getRecipientId())
//	            .orElseThrow(() -> new UserNotFoundException("Veterinarian not found with ID: " + request.getRecipientId()));
//	        existingAppointment.setVeterinarian(veterinarian);
//	    }
	    
	    return appointmenetRepository.save(existingAppointment);
	}


	@Override
	public void deleteAppointment(Long id) {
		appointmenetRepository.findById(id).ifPresentOrElse( appointmenetRepository::delete, () -> new AppointmentsNotFoundException("Appoint not trackable"));
	}

	@Override
	public Appointment getAppointmentById(Long id) {
		return appointmenetRepository.findById(id).orElseThrow(() -> new AppointmentsNotFoundException("Appoint not trackable"));
	}

	@Override
	public Appointment getAppointmentByNo(String appointmentNo) {
        return appointmenetRepository.findByAppointmentNo(appointmentNo)
                .orElseThrow(() -> new AppointmentsNotFoundException("Appointment not found with number: " + appointmentNo));
	}


	@Override
	public List<AppointmentDto> getAllAppointments() {
		
		List<Appointment> appointments = appointmenetRepository.findAll();
		
		if( appointments.isEmpty() ) {
			throw new AppointmentsNotFoundException(" No Appointments Found");
		}
		
		return appointments
				.stream()
				.sorted(Comparator.comparing(Appointment::getCreatedAt))
				.map(app -> entityConverter.entityToDto(app, AppointmentDto.class))
				.collect(Collectors.toList());
	}


	@Override
	public Appointment updateAppointmentStatus(Long appointmentId, AppointmenetStatus newStatus) {
        Appointment appointment = appointmenetRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentsNotFoundException("Appointment not found with id: " + appointmentId));
        
        if(!AppointmentStatusFlow.isTransitionValid(appointment.getStatus(), newStatus)) {
        	throw new InvalidStatusTransitionException("Cannot be glossily binded up with transition from " + appointment.getStatus() + " to " + newStatus);
        }
        appointment.setStatus(newStatus);
		return appointmenetRepository.save(appointment);
	}
	
	
}
