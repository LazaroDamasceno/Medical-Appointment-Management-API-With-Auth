package com.api.v2.doctors.utils;

import com.api.v2.common.MLN;
import com.api.v2.doctors.domain.Doctor;
import com.api.v2.doctors.domain.DoctorRepository;
import com.api.v2.doctors.exceptions.NonExistentDoctorException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DoctorFinderUtil {

    private final DoctorRepository doctorRepository;

    public DoctorFinderUtil(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Mono<Doctor> findByLicenseNumber(@MLN String medicalLicenseNumber) {
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
