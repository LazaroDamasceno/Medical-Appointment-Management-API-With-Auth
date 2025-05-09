package com.api.v1.medical_slots.utils;

 import com.api.v1.medical_slots.domain.exposed.MedicalSlot;
import com.api.v1.medical_slots.domain.MedicalSlotRepository;
import com.api.v1.medical_slots.exceptions.MedicalSlotNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public final class MedicalSlotFinder {

    private final MedicalSlotRepository medicalSlotRepository;

    public MedicalSlotFinder(MedicalSlotRepository medicalSlotRepository) {
        this.medicalSlotRepository = medicalSlotRepository;
    }

    public Mono<MedicalSlot> findById(String slotId) {
        return medicalSlotRepository
                .findById(slotId)
                .switchIfEmpty(Mono.error(new MedicalSlotNotFoundException(slotId)));
    }

    public Mono<MedicalSlot> findActiveByDoctorAndAvailableAt(String doctorId, LocalDateTime availableAt) {
        return medicalSlotRepository
                .findActiveByDoctorAndAvailableAt(doctorId, availableAt)
                .singleOptional()
                .flatMap(optional -> {
                    if (optional.isEmpty()) {
                        return Mono.error(new MedicalSlotNotFoundException());
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
                    return Mono.just(optional.get());
                });
    }
}
