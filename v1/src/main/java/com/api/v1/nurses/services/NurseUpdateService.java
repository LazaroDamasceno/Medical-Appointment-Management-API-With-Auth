package com.api.v1.nurses.services;

import com.api.v1.people.requests.PersonUpdateDTO;
import org.springframework.http.ResponseEntity;

public interface NurseUpdateService {
    ResponseEntity<Void> update(String licenseNumber, PersonUpdateDTO updateDTO);
}
