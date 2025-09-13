package com.api.v1.physicians.controller;

import com.api.v1.physicians.requests.PhysicianRegistrationDto;
import com.api.v1.physicians.services.PhysicianRegistrationService;
import com.api.v1.physicians.responses.PhysicianResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/physicians")
@RequiredArgsConstructor
public class PhysicianController {

    private final PhysicianRegistrationService registrationService;

    @PostMapping
    public ResponseEntity<PhysicianResponseDto> register(@RequestBody @Valid PhysicianRegistrationDto dto) {
        return registrationService.register(dto);
    }
}
