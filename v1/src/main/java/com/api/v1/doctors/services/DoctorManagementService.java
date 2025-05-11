package com.api.v1.doctors.services;

import com.api.v1.common.EmptyResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface DoctorManagementService {
    Mono<ResponseEntity<EmptyResponse>> terminate(String doctorLicenseNumber);
    Mono<ResponseEntity<EmptyResponse>> rehire(String doctorLicenseNumber);
}
