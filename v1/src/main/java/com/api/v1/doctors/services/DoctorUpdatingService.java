package com.api.v1.doctors.services;

import com.api.v1.common.EmptyResponse;
import com.api.v1.people.requests.PersonUpdatingDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface DoctorUpdatingService {
    Mono<ResponseEntity<EmptyResponse>> update(String doctorId, PersonUpdatingDto updatingDto);
}
