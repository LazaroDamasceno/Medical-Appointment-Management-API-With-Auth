package com.api.v1.medical_slots.services;

import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import com.api.v1.medical_slots.domain.exposed.MedicalSlot;
import com.api.v1.medical_slots.domain.MedicalSlotAuditTrail;
import com.api.v1.medical_slots.domain.MedicalSlotAuditTrailRepository;
import com.api.v1.medical_slots.domain.MedicalSlotRepository;
import com.api.v1.medical_slots.services.exposed.MedicalSlotUpdatingService;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MedicalSlotUpdatingServiceImpl implements MedicalSlotUpdatingService {

    private final MedicalSlotRepository medicalSlotRepository;
    private final MedicalSlotAuditTrailRepository auditTrailRepository;

    public MedicalSlotUpdatingServiceImpl(MedicalSlotRepository medicalSlotRepository,
                                          MedicalSlotAuditTrailRepository auditTrailRepository
    ) {
        this.medicalSlotRepository = medicalSlotRepository;
        this.auditTrailRepository = auditTrailRepository;
    }

    @Override
    public Mono<MedicalSlot> update(@NotNull MedicalSlot medicalSlot) {
        return Mono.defer(() -> {
            MedicalSlotAuditTrail auditTrail = MedicalSlotAuditTrail.of(medicalSlot);
            return auditTrailRepository
                    .save(auditTrail)
                    .flatMap(_ -> {
                        return medicalSlotRepository.save(medicalSlot);
                    });
        });
    }

    @Override
    public Mono<MedicalSlot> update(MedicalSlot medicalSlot, MedicalAppointment medicalAppointment) {
        return Mono.defer(() -> {
            MedicalSlotAuditTrail auditTrail = MedicalSlotAuditTrail.of(medicalSlot);
            return auditTrailRepository
                    .save(auditTrail)
                    .flatMap(_ -> {
                        medicalSlot.setMedicalAppointment(medicalAppointment);
                        return medicalSlotRepository.save(medicalSlot);
                    });
        });
    }
}
