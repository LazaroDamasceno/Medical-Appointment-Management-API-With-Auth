package com.api.v2.medical_appointments.services.impl;

import com.api.v2.doctors.domain.Doctor;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentCompletion;
import com.api.v2.medical_appointments.utils.MedicalAppointmentFinderUtil;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotCompletionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MedicalAppointmentCompletionImpl implements MedicalAppointmentCompletion {

    private final MedicalAppointmentRepository medicalAppointmentRepository;
    private final MedicalSlotCompletionService medicalSlotCompletionService;
    private final MedicalAppointmentFinderUtil medicalAppointmentFinderUtil;
    private final MedicalSlotRepository medicalSlotRepository;

    public MedicalAppointmentCompletionImpl(
            MedicalAppointmentRepository medicalAppointmentRepository,
            MedicalSlotCompletionService medicalSlotCompletionService,
            MedicalAppointmentFinderUtil medicalAppointmentFinderUtil,
            MedicalSlotRepository medicalSlotRepository
    ) {
        this.medicalAppointmentRepository = medicalAppointmentRepository;
        this.medicalSlotCompletionService = medicalSlotCompletionService;
        this.medicalAppointmentFinderUtil = medicalAppointmentFinderUtil;
        this.medicalSlotRepository = medicalSlotRepository;
    }

    @Override
    public Mono<Void> complete(String id) {
        return medicalAppointmentFinderUtil
                .findById(id)
                .flatMap(medicalAppointment -> {
                    AtomicReference<String> message = new AtomicReference<>(
                            "Medical appointment whose id is %s is already canceled.".formatted(id)
                    );
                    return onCanceledMedicalAppointment(medicalAppointment, message.get())
                            .then(Mono.defer(() -> {
                                message.set("Medical appointment whose id is %s is already canceled.".formatted(id));
                                return onCompletedMedicalAppointment(medicalAppointment, message.get());
                            }))
                            .then(Mono.defer(() -> {
                                medicalAppointment.markAsCompleted();
                                return medicalAppointmentRepository
                                        .save(medicalAppointment)
                                        .then(Mono.defer(() -> {
                                            LocalDateTime bookedAt = medicalAppointment.getBookedAt();
                                            Doctor doctor = medicalAppointment.getDoctor();
                                            return medicalSlotRepository
                                                    .findActiveByDoctorAndAvailableAt(bookedAt, doctor)
                                                    .flatMap(medicalSlot -> {
                                                        medicalSlot.setMedicalAppointment(medicalAppointment);
                                                        return medicalSlotCompletionService.complete(medicalSlot.getId().toString());
                                                    });
                                        }));

                            }));
                });
    }

    private Mono<Void> onCanceledMedicalAppointment(MedicalAppointment ma, String errorMessage) {
        return Mono.empty();

    }

    private Mono<Void> onCompletedMedicalAppointment(MedicalAppointment ma, String errorMessage) {
        return Mono.empty();

    }
}
