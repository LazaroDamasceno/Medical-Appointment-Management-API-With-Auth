package com.api.v1.nurses.controllers;

import com.api.v1.nurses.responses.NurseResponseDto;
import com.api.v1.nurses.services.NurseRetrievalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/nurses")
public class NurseController {

    private final NurseRetrievalService retrievalService;

    public NurseController(NurseRetrievalService retrievalService) {
        this.retrievalService = retrievalService;
    }

    @GetMapping("{nurseId}")
    public Mono<ResponseEntity<NurseResponseDto>> findById(@PathVariable String nurseId) {
        return retrievalService.findById(nurseId);
    }

    @GetMapping
    public ResponseEntity<Flux<NurseResponseDto>> findAll() {
        return retrievalService.findAll();
    }
}
