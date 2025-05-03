package com.api.v1.doctors.services;

import com.api.v1.doctors.controllers.DoctorController;
import com.api.v1.doctors.domain.DoctorRepository;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.doctors.utils.DoctorFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class DoctorRetrievalServiceImpl implements DoctorRetrievalService {

    private final DoctorRepository doctorRepository;
    private final DoctorFinder doctorFinder;

    @Override
    public Mono<DoctorResponseDto> findById(String id) {
        return doctorFinder
                .findById(id)
                .map(Doctor::toDto)
                .map(responseDto -> {
                    return responseDto.add(
                            linkTo(methodOn(DoctorController.class).findById(id)).withSelfRel(),
                            linkTo(methodOn(DoctorController.class).findAll()).withRel("find_all")
                    );
                });
    }

    @Override
    public Flux<DoctorResponseDto> findAll() {
        return doctorRepository
                .findAll()
                .map(Doctor::toDto)
                .map(responseDto -> {
                    return responseDto.add(
                            linkTo(methodOn(DoctorController.class).findAll()).withSelfRel()
                    );
                });
    }
}
