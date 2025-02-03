package com.api.v2.medical_slots.services.impl;

import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.exceptions.ImmutableMedicalSlotException;
import com.api.v2.medical_slots.services.interfaces.exposed.MedicalSlotCompletionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MedicalSlotCompletionServiceImpl implements MedicalSlotCompletionService {

    private final MedicalSlotRepository medicalSlotRepository;

    public MedicalSlotCompletionServiceImpl(MedicalSlotRepository medicalSlotRepository) {
        this.medicalSlotRepository = medicalSlotRepository;
    }

    @Override
    public Mono<Void> complete(MedicalSlot medicalSlot, MedicalAppointment medicalAppointment) {
        return onCanceledMedicalSlot(medicalSlot)
                .then(onCompletedMedicalSlot(medicalSlot))
                .then(Mono.defer(() -> {
                    medicalSlot.markAsCompleted();
                    medicalSlot.setMedicalAppointment(medicalAppointment);
                    return medicalSlotRepository
                            .save(medicalSlot)
                            .then();
                }));
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
