package com.example.CatholicPetCareSystem.exception;

public class AppointmentsNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppointmentsNotFoundException(String message) {
		super(message);
	}
}
