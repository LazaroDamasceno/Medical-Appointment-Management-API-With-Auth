package com.api.v1.nurses.services;

import com.api.v1.nurses.responses.NurseResponseDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NurseRetrievalService {
    Mono<ResponseEntity<NurseResponseDto>> findById(String nurseId);
    ResponseEntity<Flux<NurseResponseDto>> findAll();
}
