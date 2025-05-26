package com.api.v1.nurses.services;

import org.springframework.http.ResponseEntity;

public interface NurseManagementService {
    ResponseEntity<Void> terminate(String licenseNumber);
    ResponseEntity<Void> rehire(String licenseNumber);
}
