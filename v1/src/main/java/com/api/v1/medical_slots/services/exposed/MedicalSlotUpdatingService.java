package com.api.v1.medical_slots.services.exposed;

import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import com.api.v1.medical_slots.domain.MedicalSlot;
import reactor.core.publisher.Mono;

public interface MedicalSlotUpdatingService {
    Mono<MedicalSlot> update(MedicalSlot medicalSlot);
    Mono<MedicalSlot> update(MedicalSlot medicalSlot, MedicalAppointment medicalAppointment);
}
