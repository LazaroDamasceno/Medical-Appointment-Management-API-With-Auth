package com.api.v1.medical_slots.services.exposed;

import com.api.v1.medical_appointments.domain.ordinaty_appointments.exposed.MedicalAppointment;
import com.api.v1.medical_slots.domain.exposed.MedicalSlot;
import reactor.core.publisher.Mono;

public interface MedicalSlotUpdatingService {
    Mono<MedicalSlot> update(MedicalSlot medicalSlot);
    Mono<MedicalSlot> update(MedicalSlot medicalSlot, MedicalAppointment medicalAppointment);
}
