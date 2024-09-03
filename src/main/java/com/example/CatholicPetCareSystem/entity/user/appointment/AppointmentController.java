package com.example.CatholicPetCareSystem.entity.user.appointment;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.CatholicPetCareSystem.api.ApiPattern;
import com.example.CatholicPetCareSystem.dto.AppointmentDto;
import com.example.CatholicPetCareSystem.dto.EntityConverter;
import com.example.CatholicPetCareSystem.entity.user.appointment.status.AppointmenetStatus;
import com.example.CatholicPetCareSystem.exception.AppointmentAlreadyExistsException;
import com.example.CatholicPetCareSystem.exception.AppointmentsNotFoundException;
import com.example.CatholicPetCareSystem.exception.InvalidStatusTransitionException;
import com.example.CatholicPetCareSystem.exception.UserNotFoundException;
import com.example.CatholicPetCareSystem.request.AppointmentRequest;
import com.example.CatholicPetCareSystem.response.ApiResponse;
import com.example.CatholicPetCareSystem.response.FeedbackMessage;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = ApiPattern.appointment_api)
@RequiredArgsConstructor
public class AppointmentController {
	
	private final IAppointmentService appointmentService;
	
	private final EntityConverter<Appointment, AppointmentDto> entityConverter;
	
	
@RequestMapping( value = ApiPattern.appointment_registeration_api ,method = RequestMethod.POST)
  public ResponseEntity<ApiResponse<Object>> createAppointment(
		  @Valid @RequestBody AppointmentRequest request , @RequestParam("senderId") Long patientId, 
		  @RequestParam("recipientId") Long veterinarianId , BindingResult result) {
	
   if(result.hasErrors()) {
	   List<String> errorMessages = result.getFieldErrors().stream()
	            .map(fieldError -> fieldError.getDefaultMessage())
	            .collect(Collectors.toList());
	   
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	            .body(new ApiResponse<>(HttpStatus.BAD_REQUEST, "Validation failed", errorMessages));
   }

		
	try {
		Appointment appointment = appointmentService.createAppointment(request, patientId, veterinarianId);
		AppointmentDto registedAppointment = entityConverter.entityToDto(appointment, AppointmentDto.class);
		
		 return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<Object>(HttpStatus.OK, FeedbackMessage.appointment_creation_success_Message , registedAppointment));

	}
	catch(AppointmentAlreadyExistsException exception) {
		 return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<Object>(HttpStatus.BAD_REQUEST, exception.getMessage(), null));

	}
	catch(UserNotFoundException exception) {
		 return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<Object>(HttpStatus.BAD_REQUEST, exception.getMessage(), null));
	}
	 catch (Exception e) {
		 return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<Object>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
	}
 }

@RequestMapping( value = ApiPattern.appointment_update_api ,method = RequestMethod.PUT)
public ResponseEntity<ApiResponse<AppointmentDto>> updateAppointment(@PathVariable(name = "appId") Long id,
                                                     @RequestBody AppointmentRequest request) {
	try {
	    Appointment targetAppointment = appointmentService.updateAppointment(id, request);
		AppointmentDto updatedAppointment = entityConverter.entityToDto(targetAppointment, AppointmentDto.class);
		 return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<AppointmentDto>(HttpStatus.OK, FeedbackMessage.appointment_update_success_Message , updatedAppointment));

	}
	catch (AppointmentsNotFoundException e) {
		 return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<AppointmentDto>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
	}
	 catch (Exception e) {
		 return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<AppointmentDto>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
	}
}

@RequestMapping( value = ApiPattern.getAllAppointments ,method = RequestMethod.GET)
public ResponseEntity<ApiResponse<List<AppointmentDto>>> getAllAppointments() {

    try {
    	 List<AppointmentDto> appointments = appointmentService.getAllAppointments();
    	 return ResponseEntity.status(HttpStatus.OK)
    	            .body(new ApiResponse<>(HttpStatus.OK, "List of all appointments", appointments));
    	 
	} catch (AppointmentsNotFoundException e) {
		 return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<List<AppointmentDto>>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
	}
    

}

@RequestMapping( value = ApiPattern.getAppointmentById_api ,method = RequestMethod.GET)
public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable(name = "appId") Long id) {
    Appointment appointment = appointmentService.getAppointmentById(id);
	AppointmentDto updatedAppointment = entityConverter.entityToDto(appointment, AppointmentDto.class);

    return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);

}

@RequestMapping( value = ApiPattern.deleteAppointmentApi ,method = RequestMethod.DELETE)
public ResponseEntity<ApiResponse<AppointmentDto>> deleteAppointment(@PathVariable(name = "appId") Long id) {
	try {
		appointmentService.deleteAppointment(id);
		 return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse<AppointmentDto>(HttpStatus.OK, FeedbackMessage.appointment_delete_success_Message , null));
	} catch (AppointmentsNotFoundException e) {
		 return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<AppointmentDto>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
	}
}

@RequestMapping( value = ApiPattern.getAppointmentByNo ,method = RequestMethod.GET)
public ResponseEntity<ApiResponse<AppointmentDto>> getAppointmentByNo(@PathVariable(name = "appNo") String appointmentNo) {
    try {
    	Appointment appointment = appointmentService.getAppointmentByNo(appointmentNo);
        AppointmentDto appointmentDto = entityConverter.entityToDto(appointment, AppointmentDto.class);
        
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK, FeedbackMessage.APPOINTMENT_RETRIEVAL_SUCCESS, appointmentDto));
	} catch (AppointmentsNotFoundException e) {
		 return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<AppointmentDto>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
	}
}

 @RequestMapping( value = ApiPattern.updateAppStatusApi ,method = RequestMethod.PATCH)
public ResponseEntity<ApiResponse<AppointmentDto>> updateAppointmentStatus( @PathVariable("appId") Long appointmentId , @RequestParam AppointmenetStatus status) {
	   try {
           Appointment updatedAppointment = appointmentService.updateAppointmentStatus(appointmentId, status);
           AppointmentDto appointmentDto = entityConverter.entityToDto(updatedAppointment, AppointmentDto.class);
           return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Appointment status updated successfully", appointmentDto));
       } 
	   catch (InvalidStatusTransitionException e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                   .body(new ApiResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
       }
       catch (AppointmentsNotFoundException e) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND)
                       .body(new ApiResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), null));
           } 
	   catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
       }
   }
 
}
