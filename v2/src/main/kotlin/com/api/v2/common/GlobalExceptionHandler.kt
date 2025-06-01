package com.api.v2.common

import com.api.v2.customers.CustomerNotFoundException
import com.api.v2.medical_slots.InaccessibleMedicalSlotException
import com.api.v2.medical_slots.MedicalSlotNotFoundException
import com.api.v2.people.exceptions.DuplicatedEmailException
import com.api.v2.people.exceptions.DuplicatedSINException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedSINException::class)
    fun handleException(ex: DuplicatedSINException): ResponseEntity<String?> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.message)
    }

    @ExceptionHandler(DuplicatedEmailException::class)
    fun handleException(ex: DuplicatedEmailException): ResponseEntity<String?> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.message)
    }

    @ExceptionHandler(DuplicatedLicenseNumberException::class)
    fun handleException(ex: DuplicatedLicenseNumberException): ResponseEntity<String?> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.message)
    }

    @ExceptionHandler(CustomerNotFoundException::class)
    fun handleException(ex: CustomerNotFoundException): ResponseEntity<String?> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
    }

    @ExceptionHandler(DuplicatedBookingDateTimeException::class)
    fun handleException(ex: DuplicatedBookingDateTimeException): ResponseEntity<String?> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.message)
    }

    @ExceptionHandler(MedicalSlotNotFoundException::class)
    fun handleException(ex: MedicalSlotNotFoundException): ResponseEntity<String?> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
    }

    @ExceptionHandler(InaccessibleMedicalSlotException::class)
    fun handleException(ex: InaccessibleMedicalSlotException): ResponseEntity<String?> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
    }
}