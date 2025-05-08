package com.api.v1.medical_slots.utils;

import com.api.v1.doctors.domain.exposed.Doctor;
 import com.api.v1.medical_slots.domain.MedicalSlot;
import com.api.v1.medical_slots.domain.MedicalSlotRepository;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;
import com.api.v1.medical_slots.exceptions.CanceledMedicalSlotException;
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

    public Mono<MedicalSlot> findActiveByDoctorAndAvailableAt(Doctor doctor, LocalDateTime availableAt) {
        return medicalSlotRepository
                .findActiveByDoctorAndAvailableAt(doctor.getId(), availableAt)
                .singleOptional()
                .flatMap(optional -> {
                    if (optional.isEmpty()) {
                        return Mono.error(new MedicalSlotNotFoundException());
                    }
                    else if (!optional.get().getStatus().equals(MedicalSlotStatus.ACTIVE)) {
                        return Mono.error(new NotActiveMedicalSlotException());
                    }
                    return Mono.just(optional.get());
                });
    }

    public Mono<MedicalSlot> findActiveOrCompletedByMedicalAppointment(String appointmentId) {
        return medicalSlotRepository
                .findByMedicalAppointment(appointmentId)
                .singleOptional()
                .flatMap(optional -> {
                    if (optional.isEmpty()) {
                        return Mono.error(new MedicalSlotNotFoundException());
                    }
                    else if (optional.get().getStatus().equals(MedicalSlotStatus.CANCELED)) {
                        return Mono.error(new CanceledMedicalSlotException());
                    }
                    return Mono.just(optional.get());
                });
    }
}
