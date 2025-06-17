package com.api.v1.common;

import com.api.v1.customers.exceptions.CustomerNotFoundException;
import com.api.v1.doctors.exceptions.ActiveDoctorException;
import com.api.v1.doctors.exceptions.DoctorNotFoundException;
import com.api.v1.doctors.exceptions.TerminatedDoctorException;
import com.api.v1.medical_appointments.exceptions.MedicalAppointmentNotFoundException;
import com.api.v1.medical_slots.exceptions.CancelledMedicalSlotException;
import com.api.v1.medical_slots.exceptions.CompletedMedicalSlotException;
import com.api.v1.medical_slots.exceptions.InaccessibleMedicalSlotException;
import com.api.v1.medical_slots.exceptions.MedicalSlotNotFoundException;
import com.api.v1.people.exceptions.DuplicatedEmailException;
import com.api.v1.people.exceptions.DuplicatedSINException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedSINException.class)
    public ResponseEntity<String> handleException(DuplicatedSINException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<String> handleException(DuplicatedEmailException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleException(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ActiveDoctorException.class)
    public ResponseEntity<String> handleException(ActiveDoctorException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<String> handleException(DoctorNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicatedLicenseNumberException.class)
    public ResponseEntity<String> handleException(DuplicatedLicenseNumberException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(TerminatedDoctorException.class)
    public ResponseEntity<String> handleException(TerminatedDoctorException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(MedicalSlotNotFoundException.class)
    public ResponseEntity<String> handleException(MedicalSlotNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InaccessibleMedicalSlotException.class)
    public ResponseEntity<String> handleException(InaccessibleMedicalSlotException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicatedBookingDateTimeException.class)
    public ResponseEntity<String> handleException(DuplicatedBookingDateTimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(CompletedMedicalSlotException.class)
    public ResponseEntity<String> handleException(CompletedMedicalSlotException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(CancelledMedicalSlotException.class)
    public ResponseEntity<String> handleException(CancelledMedicalSlotException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
    //
    @ExceptionHandler(MedicalAppointmentNotFoundException.class)
    public ResponseEntity<String> handleException(MedicalAppointmentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
