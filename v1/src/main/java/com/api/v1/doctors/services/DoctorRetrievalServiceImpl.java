package com.api.v1.doctors.services;

import com.api.v1.doctors.controllers.DoctorController;
import com.api.v1.doctors.domain.DoctorRepository;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.doctors.utils.DoctorFinder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Service
public class DoctorRetrievalServiceImpl implements DoctorRetrievalService {

    private final DoctorRepository doctorRepository;
    private final DoctorFinder doctorFinder;

    public DoctorRetrievalServiceImpl(DoctorRepository doctorRepository,
                                      DoctorFinder doctorFinder
    ) {
        this.doctorRepository = doctorRepository;
        this.doctorFinder = doctorFinder;
    }

    @Override
    public Mono<ResponseEntity<DoctorResponseDto>> findByLicenseNumber(String licenseNumber) {
        return doctorFinder
                .findByLicenseNumber(licenseNumber)
                .map(Doctor::toDto)
                .flatMap(responseDto -> {
                    return linkTo(methodOn(DoctorController.class).findByLicenseNumber(licenseNumber))
                            .withSelfRel()
                            .toMono()
                            .map(responseDto::add)
                            .map(ResponseEntity::ok);
                })
                .flatMap(Mono::just);
    }

    @Override
    public ResponseEntity<Flux<DoctorResponseDto>>findAll(long size) {
        Flux<DoctorResponseDto> flux = doctorRepository
                .findAll()
                .map(Doctor::toDto)
                .take(size);
        return ResponseEntity.ok(flux);
    }
}
