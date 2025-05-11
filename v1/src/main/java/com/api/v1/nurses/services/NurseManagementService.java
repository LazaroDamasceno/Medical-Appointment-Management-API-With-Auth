package com.api.v1.nurses.services;

import com.api.v1.common.EmptyResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface NurseManagementService {
    Mono<ResponseEntity<EmptyResponse>> terminate(String nurseLicenseNumber);
    Mono<ResponseEntity<EmptyResponse>> rehire(String nurseLicenseNumber);
}
