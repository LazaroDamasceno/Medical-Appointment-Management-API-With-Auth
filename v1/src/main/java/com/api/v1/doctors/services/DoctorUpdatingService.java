package com.api.v1.doctors.services;

import com.api.v1.people.requests.PersonUpdatingDTO;
import org.springframework.http.ResponseEntity;

public interface DoctorUpdatingService {
    ResponseEntity<Void> update(String licenseNumber, PersonUpdatingDTO personUpdatingDto);
}
