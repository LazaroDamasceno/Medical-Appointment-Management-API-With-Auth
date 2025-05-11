package com.api.v1.nurses.services;

import com.api.v1.nurses.controllers.NurseController;
import com.api.v1.nurses.domain.NurseRepository;
import com.api.v1.nurses.domain.exposed.Nurse;
import com.api.v1.nurses.responses.NurseResponseDto;
import com.api.v1.nurses.utils.NurseFinder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Service
public class NurseRetrievalServiceImpl implements NurseRetrievalService {

    private final NurseRepository nurseRepository;
    private final NurseFinder nurseFinder;

    public NurseRetrievalServiceImpl(NurseRepository nurseRepository,
                                     NurseFinder nurseFinder
    ) {
        this.nurseRepository = nurseRepository;
        this.nurseFinder = nurseFinder;
    }

    @Override
    public Mono<ResponseEntity<NurseResponseDto>> findByLicenseNumber(String licenseNumber) {
        return nurseFinder
                .findByLicenseNumber(licenseNumber)
                .map(Nurse::toDto)
                .flatMap(response -> {
                    return Mono.zip(
                            linkTo(methodOn(NurseController.class).findByLicenseNumber(licenseNumber))
                                    .withSelfRel()
                                    .toMono(),
                            linkTo(methodOn(NurseController.class).findAll())
                                    .withRel("find all")
                                    .toMono()
                    ).map(tuple -> {
                        return response.add(tuple.getT1(), tuple.getT2());
                    }).map(ResponseEntity::ok);
                });
    }

    @Override
    public ResponseEntity<Flux<NurseResponseDto>> findAll() {
        var flux = nurseRepository
                .findAll()
                .map(Nurse::toDto);
        return ResponseEntity.ok(flux);
    }
}
