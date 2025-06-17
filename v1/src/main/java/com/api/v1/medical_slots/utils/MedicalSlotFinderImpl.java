package com.api.v1.medical_slots.utils;

import com.api.v1.common.ObjectId;
import com.api.v1.doctors.Doctor;
import com.api.v1.medical_slots.MedicalSlot;
import com.api.v1.medical_slots.MedicalSlotFinder;
import com.api.v1.medical_slots.domain.MedicalSlotCrudRepository;
import com.api.v1.medical_slots.exceptions.InaccessibleMedicalSlotException;
import com.api.v1.medical_slots.exceptions.MedicalSlotNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

    @Override
    public MedicalSlot findByIdAndDoctor(@ObjectId String id, @NotNull Doctor doctor) {
        MedicalSlot foundSlot = findById(id);
        if (!foundSlot.doctor().id().equals(doctor.id())) {
            throw new InaccessibleMedicalSlotException(id, doctor.licenseNumber());
        }
        return foundSlot;
    }
}
