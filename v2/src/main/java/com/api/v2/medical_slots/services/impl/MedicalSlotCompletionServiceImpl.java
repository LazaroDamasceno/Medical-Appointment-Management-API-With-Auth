package com.api.v2.medical_slots.services.impl;

import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotCompletionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MedicalSlotCompletionServiceImpl implements MedicalSlotCompletionService {

    private final MedicalSlotRepository medicalSlotRepository;

    public MedicalSlotCompletionServiceImpl(MedicalSlotRepository medicalSlotRepository) {
        this.medicalSlotRepository = medicalSlotRepository;
    }

    @Override
    public Mono<Void> complete(MedicalSlot medicalSlot, MedicalAppointment medicalAppointment) {
        return Mono.defer(() -> {
            medicalSlot.markAsCompleted();
            medicalSlot.setMedicalAppointment(medicalAppointment);
            return medicalSlotRepository
                    .save(medicalSlot)
                    .then();
        });
    }
}
