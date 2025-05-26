package com.api.v1.doctors.services;

import com.api.v1.people.requests.PersonUpdateDTO;
import org.springframework.http.ResponseEntity;

public interface DoctorManagementService {
    ResponseEntity<Void> terminate(String licenseNumber);
    ResponseEntity<Void> rehire(String licenseNumber);
    ResponseEntity<Void> update(String licenseNumber, PersonUpdateDTO personUpdateDTO);
}
