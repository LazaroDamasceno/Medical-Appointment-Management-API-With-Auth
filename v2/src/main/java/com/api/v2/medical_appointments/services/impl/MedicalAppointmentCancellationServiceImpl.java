package com.api.v2.medical_appointments.services.impl;

import com.api.v2.doctors.domain.Doctor;
import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.exceptions.ImmutableMedicalAppointmentException;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentCancellationService;
import com.api.v2.medical_appointments.utils.MedicalAppointmentFinderUtil;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MedicalAppointmentCancellationServiceImpl implements MedicalAppointmentCancellationService {

    private final MedicalAppointmentFinderUtil medicalAppointmentFinderUtil;
    private final MedicalSlotRepository medicalSlotRepository;
    private final MedicalAppointmentRepository medicalAppointmentRepository;

    public MedicalAppointmentCancellationServiceImpl(
            MedicalAppointmentFinderUtil medicalAppointmentFinderUtil,
            MedicalSlotRepository medicalSlotRepository,
            MedicalAppointmentRepository medicalAppointmentRepository
    ) {
        this.medicalAppointmentFinderUtil = medicalAppointmentFinderUtil;
        this.medicalSlotRepository = medicalSlotRepository;
        this.medicalAppointmentRepository = medicalAppointmentRepository;
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
                                   LocalDateTime bookedAt = medicalAppointment.getBookedAt();
                                   Doctor doctor = medicalAppointment.getDoctor();
                                   return medicalSlotRepository
                                           .findActiveByDoctorAndAvailableAt(bookedAt, doctor)
                                           .flatMap(medicalSlot -> {
                                               medicalSlot.setMedicalAppointment(null);
                                               return medicalSlotRepository
                                                       .save(medicalSlot)
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

    private Mono<Void> onCanceledMedicalAppointment(LocalDate canceledAt, String errorMessage) {
        return Mono.just(canceledAt)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.empty())
                .then(Mono.error(new ImmutableMedicalAppointmentException(errorMessage)));

    }

    private Mono<Void> onCompletedMedicalAppointment(LocalDate completedAt, String errorMessage) {
        return Mono.just(completedAt)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.empty())
                .then(Mono.error(new ImmutableMedicalAppointmentException(errorMessage)));

    }
}
