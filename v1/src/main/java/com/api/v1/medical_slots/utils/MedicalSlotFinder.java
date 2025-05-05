package com.api.v1.medical_slots.utils;

import com.api.v1.medical_slots.domain.MedicalSlot;
import com.api.v1.medical_slots.domain.MedicalSlotRepository;
import com.api.v1.medical_slots.exceptions.MedicalSlotNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MedicalSlotFinder {

    private final MedicalSlotRepository medicalSlotRepository;

    public Mono<MedicalSlot> findById(String id) {
        return medicalSlotRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new MedicalSlotNotFoundException(id)));
    }

}
