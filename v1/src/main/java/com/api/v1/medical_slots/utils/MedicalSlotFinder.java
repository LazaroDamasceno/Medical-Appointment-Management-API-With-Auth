package com.api.v1.medical_slots.utils;

import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.medical_slots.domain.MedicalSlot;
import com.api.v1.medical_slots.domain.MedicalSlotRepository;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;
import com.api.v1.medical_slots.exceptions.MedicalSlotNotFoundException;
import com.api.v1.medical_slots.exceptions.NotActiveMedicalSlotException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public final class MedicalSlotFinder {

    private final MedicalSlotRepository medicalSlotRepository;

    public Mono<MedicalSlot> findById(String slotId) {
        return medicalSlotRepository
                .findById(slotId)
                .switchIfEmpty(Mono.error(new MedicalSlotNotFoundException(slotId)));
    }

    public Mono<MedicalSlot> findActiveById(String slotId) {
        return medicalSlotRepository
                .findActiveById(slotId)
                .singleOptional()
                .flatMap(optional -> {
                    if (optional.isEmpty()) {
                        return Mono.error(new MedicalSlotNotFoundException(slotId));
                    }
                    MedicalSlot medicalSlot = optional.get();
                    if (!medicalSlot.getStatus().equals(MedicalSlotStatus.ACTIVE)) {
                        return Mono.error(new NotActiveMedicalSlotException(slotId));
                    }
                    return Mono.just(medicalSlot);
                });
    }

    public Mono<MedicalSlot> findActiveByDoctorAndAvailableAt(Doctor doctor, LocalDateTime availableAt) {
        return medicalSlotRepository
                .findActiveByDoctorAndAvailableAt(doctor, availableAt)
                .switchIfEmpty(Mono.error(new MedicalSlotNotFoundException(doctor.getId())));
    }

}
