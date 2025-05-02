package com.api.v2.doctors.utils;

import com.api.v2.doctors.domain.exposed.Doctor;
import com.api.v2.doctors.dtos.DoctorRepository;
import com.api.v2.doctors.exceptions.DoctorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DoctorFinder {

    private final DoctorRepository doctorRepository;

    public Mono<Doctor> findById(String id) {
        return doctorRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new DoctorNotFoundException(id)));
    }

}
