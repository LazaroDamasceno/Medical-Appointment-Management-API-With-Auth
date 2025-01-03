package com.api.v2.medical_slots.services.impl;

import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotCompletionService;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MedicalSlotCompletionServiceImpl implements MedicalSlotCompletionService {

    private final MedicalSlotRepository medicalSlotRepository;

    public MedicalSlotCompletionServiceImpl(MedicalSlotRepository medicalSlotRepository) {
        this.medicalSlotRepository = medicalSlotRepository;
    }

    @Override
    public Mono<Void> complete(@NotNull MedicalSlot medicalSlot) {
        return Mono.defer(() -> {
            medicalSlot.markAsCompleted();
            return medicalSlotRepository
                    .save(medicalSlot)
                    .then();
        });
    }
}
