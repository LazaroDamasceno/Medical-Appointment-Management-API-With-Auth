package com.api.v1.medical_slots.services;

import com.api.v1.common.EmptyResponse;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.utils.DoctorFinder;
import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import com.api.v1.medical_appointments.services.exposed.MedicalAppointmentUpdatingService;
import com.api.v1.medical_slots.controllers.MedicalSlotController;
import com.api.v1.medical_slots.domain.exposed.MedicalSlot;
import com.api.v1.medical_slots.domain.MedicalSlotAuditTrail;
import com.api.v1.medical_slots.domain.MedicalSlotAuditTrailRepository;
import com.api.v1.medical_slots.domain.MedicalSlotRepository;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;
import com.api.v1.medical_slots.exceptions.CanceledMedicalSlotException;
import com.api.v1.medical_slots.exceptions.CompletedMedicalSlotException;
import com.api.v1.medical_slots.exceptions.InaccessibleMedicalSlot;
import com.api.v1.medical_slots.utils.MedicalSlotFinder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Service
public class MedicalSlotManagementServiceImpl implements MedicalSlotManagementService {

    private final MedicalSlotRepository medicalSlotRepository;
    private final MedicalSlotAuditTrailRepository auditTrailRepository;
    private final DoctorFinder doctorFinder;
    private final MedicalSlotFinder medicalSlotFinder;
    private final MedicalAppointmentUpdatingService appointmentUpdatingService;

    public MedicalSlotManagementServiceImpl(MedicalSlotRepository medicalSlotRepository,
                                            MedicalSlotAuditTrailRepository auditTrailRepository,
                                            DoctorFinder doctorFinder,
                                            MedicalSlotFinder medicalSlotFinder,
                                            MedicalAppointmentUpdatingService appointmentUpdatingService
    ) {
        this.medicalSlotRepository = medicalSlotRepository;
        this.auditTrailRepository = auditTrailRepository;
        this.doctorFinder = doctorFinder;
        this.medicalSlotFinder = medicalSlotFinder;
        this.appointmentUpdatingService = appointmentUpdatingService;
    }

    @Override
    public Mono<ResponseEntity<EmptyResponse>> cancel(String doctorLicenseNumber, String slotId) {
        return doctorFinder
                .findByLicenseNumber(doctorLicenseNumber)
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
                                if (medicalSlot.getMedicalAppointment() != null) {
                                    MedicalAppointment medicalAppointment = medicalSlot.getMedicalAppointment();
                                    medicalAppointment.markAsCanceled();
                                    return appointmentUpdatingService.update(medicalAppointment)
                                            .flatMap(_ -> {
                                                return medicalSlotRepository.save(medicalSlot);
                                            });
                                }
                                medicalSlot.markAsCanceled();
                                return medicalSlotRepository.save(medicalSlot);
                            });
                })
                .flatMap(_ -> {
                    return Mono.zip(
                            linkTo(methodOn(MedicalSlotController.class)
                                    .findByDoctorAndId(doctorLicenseNumber, slotId))
                                    .withRel("find by id")
                                    .toMono(),
                            linkTo(methodOn(MedicalSlotController.class)
                                    .findAllByDoctor(doctorLicenseNumber))
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
    public Mono<ResponseEntity<EmptyResponse>> complete(String doctorLicenseNumber, String slotId) {
        return doctorFinder
                .findByLicenseNumber(doctorLicenseNumber)
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
                                if (medicalSlot.getMedicalAppointment() == null) {
                                    return Mono.error(NullPointerException::new);
                                }
                                MedicalAppointment medicalAppointment = medicalSlot.getMedicalAppointment();
                                medicalAppointment.markAsCompleted();
                                return appointmentUpdatingService
                                        .update(medicalAppointment)
                                        .flatMap(_ -> {
                                            medicalSlot.markAsCompleted();
                                            return medicalSlotRepository.save(medicalSlot);
                                        });
                            });
                })
                .flatMap(_ -> {
                    return Mono.zip(
                            linkTo(methodOn(MedicalSlotController.class)
                                    .findByDoctorAndId(doctorLicenseNumber, slotId))
                                    .withRel("find by id")
                                    .toMono(),
                            linkTo(methodOn(MedicalSlotController.class)
                                    .findAllByDoctor(doctorLicenseNumber))
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
            return Mono.error(new InaccessibleMedicalSlot(doctor.getLicenseNumber()));
        }
        return Mono.empty();
    }
}
