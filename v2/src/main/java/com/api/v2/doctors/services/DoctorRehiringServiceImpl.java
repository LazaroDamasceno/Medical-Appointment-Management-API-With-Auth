package com.api.v2.doctors.services;

import com.api.v2.doctors.domain.DoctorAuditTrail;
import com.api.v2.doctors.domain.DoctorAuditTrailRepository;
import com.api.v2.doctors.domain.DoctorRepository;
import com.api.v2.doctors.exceptions.ImmutableDoctorException;
import com.api.v2.doctors.utils.DoctorFinder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DoctorRehiringServiceImpl implements DoctorRehiringService {

    private final DoctorFinder doctorFinder;
    private final DoctorRepository doctorRepository;
    private final DoctorAuditTrailRepository doctorAuditTrailRepository;

    public DoctorRehiringServiceImpl(
            DoctorFinder doctorFinder,
            DoctorRepository doctorRepository,
            DoctorAuditTrailRepository doctorAuditTrailRepository
    ) {
        this.doctorFinder = doctorFinder;
        this.doctorRepository = doctorRepository;
        this.doctorAuditTrailRepository = doctorAuditTrailRepository;
    }

    @Override
    public Mono<Void> rehire(String medicalLicenseNumber) {
        return doctorFinder
                .findByLicenseNumber(medicalLicenseNumber)
                .flatMap(doctor -> {
                    if (doctor.getTerminatedAt() == null) {
                        String message = "Doctor whose license number is %s is employed.".formatted(doctor.getMedicalLicenseNumber());
                        return Mono.error(new ImmutableDoctorException(message));
                    }
                    DoctorAuditTrail auditTrail = DoctorAuditTrail.of(doctor);
                    return doctorAuditTrailRepository
                            .save(auditTrail)
                            .then(Mono.defer(() -> {
                                doctor.markAsRehired();
                                return doctorRepository.save(doctor);
                            }));
                })
                .then();
    }
}
