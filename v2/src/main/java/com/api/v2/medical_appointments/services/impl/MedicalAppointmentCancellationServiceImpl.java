package com.api.v2.medical_appointments.services.impl;

import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.exceptions.ImmutableMedicalAppointmentException;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentCancellationService;
import com.api.v2.medical_appointments.utils.MedicalAppointmentFinderUtil;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotCancellationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MedicalAppointmentCancellationServiceImpl implements MedicalAppointmentCancellationService {

    private final MedicalAppointmentFinderUtil medicalAppointmentFinderUtil;
    private final MedicalSlotRepository medicalSlotRepository;
    private final MedicalAppointmentRepository medicalAppointmentRepository;
    private final MedicalSlotCancellationService medicalSlotCancellationService;

    public MedicalAppointmentCancellationServiceImpl(
            MedicalAppointmentFinderUtil medicalAppointmentFinderUtil,
            MedicalSlotRepository medicalSlotRepository,
            MedicalAppointmentRepository medicalAppointmentRepository,
            MedicalSlotCancellationService medicalSlotCancellationService
    ) {
        this.medicalAppointmentFinderUtil = medicalAppointmentFinderUtil;
        this.medicalSlotRepository = medicalSlotRepository;
        this.medicalAppointmentRepository = medicalAppointmentRepository;
        this.medicalSlotCancellationService = medicalSlotCancellationService;
    }

    @Override
    public Mono<Void> cancel(String id) {
        return medicalAppointmentFinderUtil
                .findById(id)
                .flatMap(medicalAppointment -> {
                    return Mono.defer(() -> {
                        AtomicReference<String> message = new AtomicReference<>(
                                "Medical appointment whose id is %s is already canceled.".formatted(medicalAppointment.getId())
                        );
                        return onCanceledMedicalAppointment(medicalAppointment.getCanceledAt(), message.get())
                               .then(Mono.defer(() -> {
                                   message.set("Medical appointment whose id is %s is already canceled.".formatted(medicalAppointment.getId()));
                                   return onCompletedMedicalAppointment(medicalAppointment.getCompletedAt(), message.get());
                               }))
                               .then(Mono.defer(() -> {
                                   return medicalSlotRepository
                                           .findActiveByDoctorAndAvailableAt(medicalAppointment.getCanceledAt(), medicalAppointment.getDoctor())
                                           .flatMap(medicalSlot -> {
                                               return medicalSlotCancellationService
                                                       .cancel(medicalSlot.getId().toString())
                                                       .then(Mono.defer(() -> {
                                                           medicalAppointment.markAsCanceled();
                                                           return medicalAppointmentRepository.save(medicalAppointment);
                                                       }));
                                           });
                               }));
                    });
                })
                .then();
    }

    private Mono<Void> onCanceledMedicalAppointment(LocalDateTime canceledAt, String errorMessage) {
        return Mono.just(canceledAt)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.empty())
                .then(Mono.error(new ImmutableMedicalAppointmentException(errorMessage)));

    }

    private Mono<Void> onCompletedMedicalAppointment(LocalDateTime completedAt, String errorMessage) {
        return Mono.just(completedAt)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.empty())
                .then(Mono.error(new ImmutableMedicalAppointmentException(errorMessage)));

    }
}
