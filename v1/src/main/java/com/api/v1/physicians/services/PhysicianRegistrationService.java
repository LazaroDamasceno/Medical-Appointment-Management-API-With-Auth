package com.api.v1.physicians.services;

import com.api.v1.physicians.requests.PhysicianRegistrationDto;
import com.api.v1.physicians.responses.PhysicianResponseDto;
import org.springframework.http.ResponseEntity;

public interface PhysicianRegistrationService {

    ResponseEntity<PhysicianResponseDto> register(PhysicianRegistrationDto dto);
}
