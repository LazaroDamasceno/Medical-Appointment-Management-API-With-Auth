package com.api.v2.doctors;

import com.api.v2.doctors.requests.DoctorRegistrationDto;
import com.api.v2.doctors.responses.DoctorResponseDto;
import com.api.v2.doctors.services.DoctorRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorRegistrationService registrationService;

    @PostMapping
    public Mono<ResponseEntity<DoctorResponseDto>> register(@Valid @RequestBody DoctorRegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }
}
