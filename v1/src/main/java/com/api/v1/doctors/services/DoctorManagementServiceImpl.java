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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class DoctorManagementServiceImpl implements DoctorManagementService {

    private final DoctorRepository doctorRepository;
    private final DoctorAuditTrailRepository doctorAuditTrailRepository;
    private final DoctorFinder doctorFinder;

    @Override
    public Mono<ResponseEntity<EmptyResponse>> terminate(String doctorId) {
        return doctorFinder
                .findById(doctorId)
                .flatMap(foundDoctor -> {
                    return validate(foundDoctor)
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
                    return Mono.zip(
                            linkTo(methodOn(DoctorController.class).terminate(doctorId)).withSelfRel().toMono(),
                            linkTo(methodOn(DoctorController.class).findById(doctorId)).withRel("find all").toMono(),
                            linkTo(methodOn(DoctorController.class).findAll()).withRel("find by id").toMono()
                    ).map(tuple -> {
                        return EmptyResponse
                                .empty()
                                .add(
                                        tuple.getT1(),
                                        tuple.getT2(),
                                        tuple.getT3()
                                );
                    }).map(ResponseEntity::ok);
                });
    }

    @Override
    public Mono<ResponseEntity<EmptyResponse>> rehire(String doctorId) {
        return doctorFinder
                .findById(doctorId)
                .flatMap(foundDoctor -> {
                    return validate(foundDoctor)
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
                    return Mono.zip(
                            linkTo(methodOn(DoctorController.class).rehire(doctorId)).withSelfRel().toMono(),
                            linkTo(methodOn(DoctorController.class).findById(doctorId)).withRel("find all").toMono(),
                            linkTo(methodOn(DoctorController.class).findAll()).withRel("find by id").toMono()
                    ).map(tuple -> {
                        return EmptyResponse
                                .empty()
                                .add(
                                        tuple.getT1(),
                                        tuple.getT2(),
                                        tuple.getT3()
                                );
                    }).map(ResponseEntity::ok);
                });
    }

    private Mono<Object> validate(Doctor doctor) {
        if (doctor.getStatus().equals(DoctorStatus.TERMINATE)) {
            return Mono.error(new TerminatedDoctorException(doctor.getId()));
        }
        else if (doctor.getStatus().equals(DoctorStatus.ACTIVE)) {
            return Mono.error(new ActiveDoctorException(doctor.getId()));
        }
        return Mono.empty();
    }
}
