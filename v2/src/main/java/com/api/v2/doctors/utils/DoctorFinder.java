package com.api.v2.doctors.utils;

import com.api.v2.doctors.domain.Doctor;
import com.api.v2.doctors.domain.DoctorRepository;
import com.api.v2.doctors.exceptions.NonExistentDoctorException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DoctorFinder {

    private final DoctorRepository doctorRepository;

    public DoctorFinder(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Mono<Doctor> findByLicenseNumber(String medicalLicenseNumber) {
        return doctorRepository
                .findAll()
                .filter(d -> d.getMedicalLicenseNumber().equals(medicalLicenseNumber))
                .singleOrEmpty()
                .singleOptional()
                .flatMap(optional -> {
                    if (optional.isEmpty()) {
                        return Mono.error(new NonExistentDoctorException(medicalLicenseNumber));
                    }
                    return Mono.just(optional.get());
                });
    }
}
