package com.api.v1.doctors.utils;

import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.domain.DoctorRepository;
import com.api.v1.doctors.exceptions.DoctorNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public final class DoctorFinder {

    private final DoctorRepository doctorRepository;

    public DoctorFinder(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Mono<Doctor> findById(String doctorId) {
        return doctorRepository
                .findById(doctorId)
                .switchIfEmpty(Mono.error(new DoctorNotFoundException()));
    }

}
