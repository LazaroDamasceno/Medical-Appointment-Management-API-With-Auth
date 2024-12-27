package com.api.v2.medical_slots.services.impl;

import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotCompletionService;
import com.api.v2.medical_slots.utils.MedicalSlotFinderUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class MedicalSlotCompletionServiceImpl implements MedicalSlotCompletionService {

    private final MedicalSlotFinderUtil medicalSlotFinderUtil;
    private final MedicalSlotRepository medicalSlotRepository;

    public MedicalSlotCompletionServiceImpl(
            MedicalSlotFinderUtil medicalSlotFinderUtil,
            MedicalSlotRepository medicalSlotRepository
    ) {
        this.medicalSlotFinderUtil = medicalSlotFinderUtil;
        this.medicalSlotRepository = medicalSlotRepository;
    }

    @Override
    public Mono<Void> complete(String id) {
        return medicalSlotFinderUtil
                .findById(id)
                .flatMap(slot -> {
                    AtomicReference<String> message = new AtomicReference<>("Medical slot whose id is %s is already canceled.".formatted(id));
                    return onCanceledMedicalSlot(slot, message.get())
                            .then(Mono.defer(() -> {
                                message.set("Medical slot whose id is %s is already completed.".formatted(id));
                                return onCompletedMedicalSlot(slot, message.get());
                            }))
                            .then(Mono.defer(() -> {
                                slot.markAsCompleted();
                                return medicalSlotRepository.save(slot);
                            }));
                })
                .then();
    }

    private Mono<Void> onCanceledMedicalSlot(MedicalSlot slot, String errorMessage) {
        return Mono.empty();
    }

    private Mono<Void> onCompletedMedicalSlot(MedicalSlot slot, String errorMessage) {
        return Mono.empty();
    }
}
