package com.api.v1.medical_slots.services;

import org.springframework.http.ResponseEntity;

public interface MedicalSlotManagementService {
    ResponseEntity<Void> cancel(String medicalLicenseNumber, String slotId);
    ResponseEntity<Void> complete(String medicalLicenseNumber, String slotId);

}
