package com.api.v2.common;

import com.api.v2.cards.exceptions.NonExistentCardException;
import com.api.v2.customers.exceptions.NonExistentCustomerException;
import com.api.v2.doctors.exceptions.DuplicatedMedicalLicenseNumberException;
import com.api.v2.doctors.exceptions.ImmutableDoctorException;
import com.api.v2.doctors.exceptions.NonExistentDoctorException;
import com.api.v2.medical_appointments.exceptions.ImmutableMedicalAppointmentException;
import com.api.v2.medical_appointments.exceptions.InaccessibleMedicalAppointmentException;
import com.api.v2.medical_appointments.exceptions.NonExistentMedicalAppointmentException;
import com.api.v2.medical_slots.exceptions.ImmutableMedicalSlotException;
import com.api.v2.medical_slots.exceptions.InaccessibleMedicalSlotException;
import com.api.v2.medical_slots.exceptions.NonExistentMedicalSlotException;
import com.api.v2.payments.exceptions.IllegalChargingException;
import com.api.v2.people.exceptions.DuplicatedEmailException;
import com.api.v2.people.exceptions.DuplicatedSsnException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedSsnException.class)
    public ResponseEntity<String> handleException(DuplicatedSsnException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<String> handleException(DuplicatedEmailException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicatedMedicalLicenseNumberException.class)
    public ResponseEntity<String> handleException(DuplicatedMedicalLicenseNumberException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ImmutableDoctorException.class)
    public ResponseEntity<String> handleException(ImmutableDoctorException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InaccessibleMedicalAppointmentException.class)
    public ResponseEntity<String> handleException(InaccessibleMedicalAppointmentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ImmutableMedicalAppointmentException.class)
    public ResponseEntity<String> handleException(ImmutableMedicalAppointmentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InaccessibleMedicalSlotException.class)
    public ResponseEntity<String> handleException(InaccessibleMedicalSlotException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ImmutableMedicalSlotException.class)
    public ResponseEntity<String> handleException(ImmutableMedicalSlotException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(BlockedBookingDateTimeException.class)
    public ResponseEntity<String> handleException(BlockedBookingDateTimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalChargingException.class)
    public ResponseEntity<String> handleException(IllegalChargingException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(NonExistentMedicalAppointmentException.class)
    public ResponseEntity<String> handleException(NonExistentMedicalAppointmentException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NonExistentCustomerException.class)
    public ResponseEntity<String> handleException(NonExistentCustomerException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NonExistentDoctorException.class)
    public ResponseEntity<String> handleException(NonExistentDoctorException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NonExistentMedicalSlotException.class)
    public ResponseEntity<String> handleException(NonExistentMedicalSlotException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NonExistentCardException.class)
    public ResponseEntity<String> handleException(NonExistentCardException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
