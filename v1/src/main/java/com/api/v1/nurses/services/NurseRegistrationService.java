package com.api.v1.nurses.services;

import com.api.v1.nurses.responses.NurseResponseDto;
import com.api.v1.nurses.resquests.NurseRegistrationDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface NurseRegistrationService {
    Mono<ResponseEntity<NurseResponseDto>> register(NurseRegistrationDto registrationDto);
}
