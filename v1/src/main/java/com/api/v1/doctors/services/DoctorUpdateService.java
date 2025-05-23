package com.api.v1.doctors.services;

import com.api.v1.people.requests.PersonUpdateDTO;
import org.springframework.http.ResponseEntity;

public interface DoctorUpdateService {
    ResponseEntity<Void> update(String licenseNumber, PersonUpdateDTO personUpdateDTO);
}
