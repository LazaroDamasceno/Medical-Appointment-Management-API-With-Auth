package com.api.v2.utils;

import com.api.v2.domain.Doctor;
import com.api.v2.domain.DoctorRepository;
import com.api.v2.exceptions.NonExistentDoctorException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DoctorFinderUtil {

    private final DoctorRepository doctorRepository;

    public DoctorFinderUtil(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Mono<Doctor> findByLicenseNumber(String medicalLicenseNumber) {
        return doctorRepository
                .findByLicenseNumber(medicalLicenseNumber)
                .singleOptional()
                .flatMap(optional -> {
                    if (optional.isEmpty()) {
                        return Mono.error(new NonExistentDoctorException(medicalLicenseNumber));
                    }
                    return Mono.just(optional.get());
                });
    }
}
