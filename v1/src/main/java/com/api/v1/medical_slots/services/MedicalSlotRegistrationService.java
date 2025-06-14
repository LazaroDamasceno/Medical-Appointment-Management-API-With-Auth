package com.api.v1.medical_slots.services;

import com.api.v1.medical_slots.MedicalSlotResponseDTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface MedicalSlotRegistrationService {
    ResponseEntity<MedicalSlotResponseDTO> register(String medicalLicenseNumber, LocalDateTime availableAt);
}
