package com.api.v2.medical_slots.utils;

import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.exceptions.NonExistentMedicalSlotException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MedicalSlotFinderUtil {

    private final MedicalSlotRepository repository;

    public MedicalSlotFinderUtil(MedicalSlotRepository repository) {
        this.repository = repository;
    }

    public Mono<MedicalSlot> findById(String id) {
        return repository
                .findById(new ObjectId(id))
                .singleOptional()
                .flatMap(optional -> {
                   if (optional.isEmpty()) {
                       return Mono.error(new NonExistentMedicalSlotException(new ObjectId(id)));
                   }
                   return Mono.just(optional.get());
                });
    }
}
