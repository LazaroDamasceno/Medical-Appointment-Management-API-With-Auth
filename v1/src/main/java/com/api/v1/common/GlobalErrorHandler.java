package com.api.v1.common;

import com.api.v1.customers.exceptions.*;
import com.api.v1.doctors.exceptions.*;
import com.api.v1.medical_appointments.exceptions.*;
import com.api.v1.medical_slots.exceptions.*;
import com.api.v1.nurses.exceptions.*;
import com.api.v1.people.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(DuplicatedSinException.class)
    public ResponseEntity<String> handleException(DuplicatedSinException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<String> handleException(DuplicatedEmailException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(CanceledMedicalSlotException.class)
    public ResponseEntity<String> handleException(CanceledMedicalSlotException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(CompletedMedicalSlotException.class)
    public ResponseEntity<String> handleException(CompletedMedicalSlotException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(InaccessibleMedicalSlot.class)
    public ResponseEntity<String> handleException(InaccessibleMedicalSlot ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(MedicalSlotNotFoundException.class)
    public ResponseEntity<String> handleException(MedicalSlotNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NotActiveMedicalSlotException.class)
    public ResponseEntity<String> handleException(NotActiveMedicalSlotException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ActiveMedicalAppointmentException.class)
    public ResponseEntity<String> handleException(ActiveMedicalAppointmentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(CanceledMedicalAppointmentException.class)
    public ResponseEntity<String> handleException(CanceledMedicalAppointmentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(CompletedMedicalAppointmentException.class)
    public ResponseEntity<String> handleException(CompletedMedicalAppointmentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(InaccessibleMedicalAppointment.class)
    public ResponseEntity<String> handleException(InaccessibleMedicalAppointment ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(MedicalAppointmentNotFoundException.class)
    public ResponseEntity<String> handleException(MedicalAppointmentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(PaidMedicalAppointmentException.class)
    public ResponseEntity<String> handleException(PaidMedicalAppointmentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(DoctorNotFoundException .class)
    public ResponseEntity<String> handleException(DoctorNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ActiveDoctorException.class)
    public ResponseEntity<String> handleException(ActiveDoctorException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicatedMedicalLicenseNumberException.class)
    public ResponseEntity<String> handleException(DuplicatedMedicalLicenseNumberException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(TerminatedDoctorException.class)
    public ResponseEntity<String> handleException(TerminatedDoctorException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleException(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicatedBookingDateTimeException.class)
    public ResponseEntity<String> handleException(DuplicatedBookingDateTimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(NonExistentBookingDateTimeException.class)
    public ResponseEntity<String> handleException(NonExistentBookingDateTimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicatedNurseLicenseNumberException.class)
    public ResponseEntity<String> handleException(DuplicatedNurseLicenseNumberException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NurseNotFoundException.class)
    public ResponseEntity<String> handleException(NurseNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
