package com.api.v1.doctors.services;

import com.api.v1.doctors.controllers.DoctorController;
import com.api.v1.doctors.domain.DoctorRepository;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.doctors.utils.DoctorFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class DoctorRetrievalServiceImpl implements DoctorRetrievalService {

    private final DoctorRepository doctorRepository;
    private final DoctorFinder doctorFinder;

    @Override
    public Mono<ResponseEntity<DoctorResponseDto>> findById(String id) {
        return doctorFinder
                .findById(id)
                .map(Doctor::toDto)
                .flatMap(responseDto -> {
                    return Mono.zip(
                            linkTo(methodOn(DoctorController.class).findById(id)).withSelfRel().toMono(),
                            linkTo(methodOn(DoctorController.class).findAll()).withRel("find all").toMono()
                    ).map(tuple -> {
                        return responseDto.add(tuple.getT1(), tuple.getT2());
                    }).map(ResponseEntity::ok);
                })
                .flatMap(Mono::just);
    }

    @Override
    public ResponseEntity<Flux<DoctorResponseDto>>findAll() {
        Flux<DoctorResponseDto> flux = doctorRepository
                .findAll()
                .map(Doctor::toDto);
        return ResponseEntity.ok(flux);
    }
}
