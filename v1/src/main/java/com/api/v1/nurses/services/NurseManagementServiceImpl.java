package com.api.v1.nurses.services;

import com.api.v1.common.EmptyResponse;
import com.api.v1.common.ProfessionalStatus;
import com.api.v1.nurses.controllers.NurseController;
import com.api.v1.nurses.domain.NurseAuditTrail;
import com.api.v1.nurses.domain.NurseAuditTrailRepository;
import com.api.v1.nurses.domain.NurseRepository;
import com.api.v1.nurses.domain.exposed.Nurse;
import com.api.v1.nurses.exceptions.TerminatedNurseException;
import com.api.v1.nurses.utils.NurseFinder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Service
public class NurseManagementServiceImpl implements NurseManagementService {

    private final NurseAuditTrailRepository auditTrailRepository;
    private final NurseRepository nurseRepository;
    private final NurseFinder nurseFinder;

    public NurseManagementServiceImpl(NurseAuditTrailRepository auditTrailRepository,
                                      NurseRepository nurseRepository,
                                      NurseFinder nurseFinder
    ) {
        this.auditTrailRepository = auditTrailRepository;
        this.nurseRepository = nurseRepository;
        this.nurseFinder = nurseFinder;
    }

    @Override
    public Mono<ResponseEntity<EmptyResponse>> terminate(String nurseLicenseNumber) {
        return nurseFinder
                .findByLicenseNumber(nurseLicenseNumber)
                .flatMap(foundNurse -> {
                    return onTerminatedNurse(foundNurse)
                            .then(Mono.defer(() -> {
                                NurseAuditTrail auditTrail = NurseAuditTrail.of(foundNurse);
                                return auditTrailRepository
                                        .save(auditTrail)
                                        .flatMap(_ -> {
                                            foundNurse.markAsTerminated();
                                            return nurseRepository.save(foundNurse);
                                        });
                            }));
                })
                .flatMap(_ -> hateoas(nurseLicenseNumber));
    }

    @Override
    public Mono<ResponseEntity<EmptyResponse>> rehire(String nurseLicenseNumber) {
        return nurseFinder
                .findByLicenseNumber(nurseLicenseNumber)
                .flatMap(foundNurse -> {
                    return onActiveNurse(foundNurse)
                            .then(Mono.defer(() -> {
                                NurseAuditTrail auditTrail = NurseAuditTrail.of(foundNurse);
                                return auditTrailRepository
                                        .save(auditTrail)
                                        .flatMap(_ -> {
                                            foundNurse.markAsRehired();
                                            return nurseRepository.save(foundNurse);
                                        });
                            }));
                })
                .flatMap(_ -> hateoas(nurseLicenseNumber));
    }

    private Mono<ResponseEntity<EmptyResponse>> hateoas(String nurseLicenseNumber) {
        return Mono.zip(
                linkTo(methodOn(NurseController.class).findByLicenseNumber(nurseLicenseNumber))
                        .withRel("find by license number")
                        .toMono(),
                linkTo(methodOn(NurseController.class).findAll())
                        .withRel("find all")
                        .toMono()
        ).map(links -> {
            return EmptyResponse
                    .empty()
                    .add(links.getT1(), links.getT2());
        }).map(ResponseEntity::ok);
    }

    private Mono<Object> onTerminatedNurse(Nurse nurse) {
        if (nurse.getStatus().equals(ProfessionalStatus.TERMINATED)) {
            return Mono.error(new TerminatedNurseException(nurse.getLicenseNumber()));
        }
        return Mono.empty();
    }

    private Mono<Object> onActiveNurse(Nurse nurse) {
        if (nurse.getStatus().equals(ProfessionalStatus.ACTIVE)) {
            return Mono.error(new TerminatedNurseException(nurse.getLicenseNumber()));
        }
        return Mono.empty();
    }
}
