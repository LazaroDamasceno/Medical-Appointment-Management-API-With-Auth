package com.api.v1.medical_slots.utils;

import com.api.v1.common.ObjectId;
import com.api.v1.medical_slots.MedicalSlot;
import com.api.v1.medical_slots.MedicalSlotFinder;
import com.api.v1.medical_slots.domain.MedicalSlotCrudRepository;
import com.api.v1.medical_slots.exceptions.MedicalSlotNotFoundException;
import org.springframework.stereotype.Component;

@Component
public final class MedicalSlotFinderImpl implements MedicalSlotFinder {

    private final MedicalSlotCrudRepository crudRepository;

    public MedicalSlotFinderImpl(MedicalSlotCrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public MedicalSlot findById(@ObjectId String id) {
        return crudRepository
                .findById(id)
                .orElseThrow(() -> new MedicalSlotNotFoundException(id));
    }
}
