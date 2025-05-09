package com.api.v1.doctors.services;

import com.api.v1.common.EmptyResponse;
import com.api.v1.doctors.controllers.DoctorController;
import com.api.v1.doctors.domain.DoctorAuditTrail;
import com.api.v1.doctors.domain.DoctorAuditTrailRepository;
import com.api.v1.doctors.domain.DoctorRepository;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.enums.DoctorStatus;
import com.api.v1.doctors.exceptions.ActiveDoctorException;
import com.api.v1.doctors.exceptions.TerminatedDoctorException;
import com.api.v1.doctors.utils.DoctorFinder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Service
public class DoctorManagementServiceImpl implements DoctorManagementService {

    private final DoctorRepository doctorRepository;
    private final DoctorAuditTrailRepository doctorAuditTrailRepository;
    private final DoctorFinder doctorFinder;

    public DoctorManagementServiceImpl(DoctorRepository doctorRepository,
                                       DoctorAuditTrailRepository doctorAuditTrailRepository,
                                       DoctorFinder doctorFinder
    ) {
        this.doctorRepository = doctorRepository;
        this.doctorAuditTrailRepository = doctorAuditTrailRepository;
        this.doctorFinder = doctorFinder;
    }

    @Override
    public Mono<ResponseEntity<EmptyResponse>> terminate(String doctorId) {
        return doctorFinder
                .findById(doctorId)
                .flatMap(foundDoctor -> {
                    return onTerminatedDoctor(foundDoctor)
                            .then(Mono.defer(() -> {
                                DoctorAuditTrail doctorAuditTrail = DoctorAuditTrail.of(foundDoctor);
                                return doctorAuditTrailRepository
                                        .save(doctorAuditTrail)
                                        .flatMap(_ -> {
                                            foundDoctor.markAsTerminated();
                                            return doctorRepository.save(foundDoctor);
                                        });
                            }));
                })
                .flatMap(_ -> {
                    return hateoas(doctorId);
                });
    }

    @Override
    public Mono<ResponseEntity<EmptyResponse>> rehire(String doctorId) {
        return doctorFinder
                .findById(doctorId)
                .flatMap(foundDoctor -> {
                    return onActiveDoctor(foundDoctor)
                            .then(Mono.defer(() -> {
                                DoctorAuditTrail doctorAuditTrail = DoctorAuditTrail.of(foundDoctor);
                                return doctorAuditTrailRepository
                                        .save(doctorAuditTrail)
                                        .flatMap(_ -> {
                                            foundDoctor.markAsRehired();
                                            return doctorRepository.save(foundDoctor);
                                        });
                            }));
                })
                .flatMap(_ -> {
                    return hateoas(doctorId);
                });
    }

    private Mono<Object> onTerminatedDoctor(Doctor doctor) {
        if (doctor.getStatus().equals(DoctorStatus.TERMINATED)) {
            return Mono.error(new TerminatedDoctorException(doctor.getId()));
        }
        return Mono.empty();
    }

    private Mono<Object> onActiveDoctor(Doctor doctor) {
        if (doctor.getStatus().equals(DoctorStatus.ACTIVE)) {
            return Mono.error(new ActiveDoctorException(doctor.getId()));
        }
        return Mono.empty();
    }

    private Mono<ResponseEntity<EmptyResponse>> hateoas(String doctorId) {
        return Mono.zip(
                linkTo(methodOn(DoctorController.class).findById(doctorId)).withRel("find by id").toMono(),
                linkTo(methodOn(DoctorController.class).findAll()).withRel("find all").toMono()
        ).map(tuple -> {
            return EmptyResponse
                    .empty()
                    .add(tuple.getT1(), tuple.getT2());
        }).map(ResponseEntity::ok);
    }
}
