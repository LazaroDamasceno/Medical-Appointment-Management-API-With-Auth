package com.api.v2.medical_slots.services.impl;

import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.exceptions.ImmutableMedicalAppointmentException;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.exceptions.ImmutableMedicalSlotException;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotCancellationService;
import com.api.v2.medical_slots.utils.MedicalSlotFinderUtil;

import java.util.Optional;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MedicalSlotCancellationServiceImpl implements MedicalSlotCancellationService {

    private final MedicalSlotFinderUtil medicalSlotFinderUtil;
    private final MedicalSlotRepository medicalSlotRepository;
    private final MedicalAppointmentRepository medicalAppointmentRepository;

    public MedicalSlotCancellationServiceImpl(
            MedicalSlotFinderUtil medicalSlotFinderUtil,
            MedicalSlotRepository medicalSlotRepository,
            MedicalAppointmentRepository medicalAppointmentRepository
    ) {
        this.medicalSlotFinderUtil = medicalSlotFinderUtil;
        this.medicalSlotRepository = medicalSlotRepository;
        this.medicalAppointmentRepository = medicalAppointmentRepository;
    }

    @Override
    public Mono<Void> cancel(String id) {
        return medicalSlotFinderUtil
                .findById(id)
                .flatMap(slot -> {
                    return onCanceledMedicalSlot(slot)
                            .then(onCompletedMedicalSlot(slot))
                            .then(Mono.defer(() -> {
                                var optional = Optional.ofNullable(slot.getMedicalAppointment());
                                if (optional.isPresent()) {
                                    slot.markAsCanceled();
                                    MedicalAppointment medicalAppointment = slot.getMedicalAppointment();
                                    medicalAppointment.markAsCanceled();
                                    slot.setMedicalAppointment(medicalAppointment);
                                    return medicalSlotRepository
                                            .save(slot)
                                            .then(medicalAppointmentRepository.save(medicalAppointment));
                                }
                                slot.markAsCanceled();
                                return medicalSlotRepository.save(slot);
                            }));
                })
                .then();
    }

    private Mono<Void> onCanceledMedicalSlot(MedicalSlot slot) {
        String message = "Medical slot whose id is %s is already canceled.".formatted(slot.getId());
        if (slot.getCompletedAt() == null && slot.getCanceledAt() != null) {
            return Mono.error(new ImmutableMedicalSlotException(message));
        }
        return Mono.empty();
    }

    private Mono<Void> onCompletedMedicalSlot(MedicalSlot slot) {
        String message = "Medical slot whose id is %s is already completed.".formatted(slot.getId());
        if (slot.getCompletedAt() != null && slot.getCanceledAt() == null) {
            return Mono.error(new ImmutableMedicalSlotException(message));
        }
        return Mono.empty();
    }
}
