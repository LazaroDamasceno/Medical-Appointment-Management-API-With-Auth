package com.api.v2.medical_slots.services.interfaces;

import com.api.v2.common.MLN;

import reactor.core.publisher.Mono;

public interface MedicalSlotCancellationService {
    Mono<Void> cancel(@MLN String medicalLicenseNumber, String slotId);
}
