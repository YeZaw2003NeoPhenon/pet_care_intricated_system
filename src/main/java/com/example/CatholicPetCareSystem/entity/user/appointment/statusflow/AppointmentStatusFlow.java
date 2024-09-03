package com.example.CatholicPetCareSystem.entity.user.appointment.statusflow;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.example.CatholicPetCareSystem.entity.user.appointment.status.AppointmenetStatus;

public class AppointmentStatusFlow {
	
 private static final Map<AppointmenetStatus, EnumSet<AppointmenetStatus>> validTransitions = new HashMap<>();
	
	static {
		validTransitions.put(AppointmenetStatus.WAITING_FOR_APPROVAL, EnumSet.of(AppointmenetStatus.APPROVED , AppointmenetStatus.CANCELED));
        validTransitions.put(AppointmenetStatus.APPROVED, EnumSet.of(AppointmenetStatus.ON_GOING, AppointmenetStatus.CANCELED));
        validTransitions.put(AppointmenetStatus.ON_GOING, EnumSet.of(AppointmenetStatus.COMPLETED, AppointmenetStatus.CANCELED));
        validTransitions.put(AppointmenetStatus.COMPLETED, EnumSet.noneOf(AppointmenetStatus.class));
        validTransitions.put(AppointmenetStatus.CANCELED, EnumSet.noneOf(AppointmenetStatus.class));
	}
	
	public static boolean isTransitionValid(AppointmenetStatus currentStatus , AppointmenetStatus newStatus ) {
		return validTransitions.getOrDefault(currentStatus, EnumSet.noneOf(AppointmenetStatus.class)).contains(newStatus);
	}
}
