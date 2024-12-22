package com.api.v2.doctors.services.impl;

import com.api.v2.doctors.domain.DoctorRepository;
import com.api.v2.doctors.exceptions.ImmutableDoctorException;
import com.api.v2.doctors.services.DoctorRehiringService;
import com.api.v2.doctors.utils.DoctorFinderUtil;
import reactor.core.publisher.Mono;

public class DoctorRehiringServiceImpl implements DoctorRehiringService {

    private final DoctorFinderUtil doctorFinderUtil;
    private final DoctorRepository doctorRepository;

    public DoctorRehiringServiceImpl(
            DoctorFinderUtil doctorFinderUtil,
            DoctorRepository doctorRepository
    ) {
        this.doctorFinderUtil = doctorFinderUtil;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Mono<Void> rehire(String medicalLicenseNumber) {
        return doctorFinderUtil
                .findByLicenseNumber(medicalLicenseNumber)
                .flatMap(doctor -> {
                    if (doctor.getTerminatedAt() == null) {
                        String message = "Doctor whose license number is %s is employed.".formatted(doctor.getLicenseNumber());
                        return Mono.error(new ImmutableDoctorException(message));
                    }
                    doctor.markAsRehired();
                    return doctorRepository.save(doctor);
                })
                .then();
    }
}
