package com.api.v2.medical_slots.services.impl;

import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.exceptions.ImmutableMedicalSlotException;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotCompletionService;
import com.api.v2.medical_slots.utils.MedicalSlotFinderUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Objects;
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
                    return onCanceledMedicalSlot(slot.getCanceledAt(), message.get())
                            .then(Mono.defer(() -> {
                                message.set("Medical slot whose id is %s is already completed.".formatted(id));
                                return onCompletedMedicalSlot(slot.getCompletedAt(), message.get());
                            }))
                            .then(Mono.defer(() -> {
                                slot.markAsCompleted();
                                return medicalSlotRepository.save(slot);
                            }));
                })
                .then();
    }

    private Mono<Void> onCanceledMedicalSlot(LocalDate canceledAt, String errorMessage) {
        return Mono.just(canceledAt)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.empty())
                .then(Mono.error(new ImmutableMedicalSlotException(errorMessage)));
    }

    private Mono<Void> onCompletedMedicalSlot(LocalDate completedAt, String errorMessage) {
        return Mono.just(completedAt)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.empty())
                .then(Mono.error(new ImmutableMedicalSlotException(errorMessage)));
    }
}
