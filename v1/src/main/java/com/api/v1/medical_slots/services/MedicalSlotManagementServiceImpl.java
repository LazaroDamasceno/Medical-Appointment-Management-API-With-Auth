package com.api.v1.medical_slots.services;

import com.api.v1.common.EmptyResponse;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.utils.DoctorFinder;
import com.api.v1.medical_slots.controllers.MedicalSlotController;
import com.api.v1.medical_slots.domain.MedicalSlot;
import com.api.v1.medical_slots.domain.MedicalSlotAuditTrail;
import com.api.v1.medical_slots.domain.MedicalSlotAuditTrailRepository;
import com.api.v1.medical_slots.domain.MedicalSlotRepository;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;
import com.api.v1.medical_slots.exceptions.CanceledMedicalSlotException;
import com.api.v1.medical_slots.exceptions.CompletedMedicalSlotException;
import com.api.v1.medical_slots.exceptions.InaccessibleMedicalSlot;
import com.api.v1.medical_slots.utils.MedicalSlotFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class MedicalSlotManagementServiceImpl implements MedicalSlotManagementService {

    private final MedicalSlotRepository medicalSlotRepository;
    private final MedicalSlotAuditTrailRepository auditTrailRepository;
    private final DoctorFinder doctorFinder;
    private final MedicalSlotFinder medicalSlotFinder;

    @Override
    public Mono<ResponseEntity<EmptyResponse>> cancel(String doctorId, String slotId) {
        return doctorFinder
                .findById(doctorId)
                .zipWith(medicalSlotFinder.findById(slotId))
                .flatMap(tuple -> {
                    Doctor doctor = tuple.getT1();
                    MedicalSlot medicalSlot = tuple.getT2();
                    return validate(doctor, medicalSlot)
                            .then(Mono.defer(() -> {
                                MedicalSlotAuditTrail auditTrail = MedicalSlotAuditTrail.of(medicalSlot);
                                return auditTrailRepository.save(auditTrail);
                            }))
                            .flatMap(_ -> {
                                medicalSlot.markAsCanceled();
                                return medicalSlotRepository.save(medicalSlot);
                            });
                })
                .flatMap(_ -> {
                    return Mono.zip(
                            linkTo(methodOn(MedicalSlotController.class)
                                    .findByDoctorAndId(doctorId, slotId))
                                    .withRel("find by id")
                                    .toMono(),
                            linkTo(methodOn(MedicalSlotController.class)
                                    .findAllByDoctor(doctorId))
                                    .withRel("find all")
                                    .toMono()
                    ).map(links -> {
                        return EmptyResponse
                                .empty()
                                .add(links.getT1(), links.getT2());
                    }).map(ResponseEntity::ok);
                });
    }

    @Override
    public Mono<ResponseEntity<EmptyResponse>> complete(String doctorId, String slotId) {
        return doctorFinder
                .findById(doctorId)
                .zipWith(medicalSlotFinder.findById(slotId))
                .flatMap(tuple -> {
                    Doctor doctor = tuple.getT1();
                    MedicalSlot medicalSlot = tuple.getT2();
                    return validate(doctor, medicalSlot)
                            .then(Mono.defer(() -> {
                                MedicalSlotAuditTrail auditTrail = MedicalSlotAuditTrail.of(medicalSlot);
                                return auditTrailRepository.save(auditTrail);
                            }))
                            .flatMap(_ -> {
                                medicalSlot.markAsCompleted();
                                return medicalSlotRepository.save(medicalSlot);
                            });
                })
                .flatMap(_ -> {
                    return Mono.zip(
                            linkTo(methodOn(MedicalSlotController.class)
                                    .findByDoctorAndId(doctorId, slotId))
                                    .withRel("find by id")
                                    .toMono(),
                            linkTo(methodOn(MedicalSlotController.class)
                                    .findAllByDoctor(doctorId))
                                    .withRel("find all")
                                    .toMono()
                    ).map(links -> {
                        return EmptyResponse
                                .empty()
                                .add(links.getT1(), links.getT2());
                    }).map(ResponseEntity::ok);
                });
    }

    private Mono<Object> validate(Doctor doctor, MedicalSlot medicalSlot) {
        if (medicalSlot.getStatus().equals(MedicalSlotStatus.CANCELED)) {
            return Mono.error(new CanceledMedicalSlotException(medicalSlot.getId()));
        }
        else if (medicalSlot.getStatus().equals(MedicalSlotStatus.COMPLETED)) {
            return Mono.error(new CompletedMedicalSlotException(medicalSlot.getId()));
        }
        else if (!medicalSlot.getDoctor().getId().equals(doctor.getId())) {
            return Mono.error(new InaccessibleMedicalSlot());
        }
        return Mono.empty();
    }
}
