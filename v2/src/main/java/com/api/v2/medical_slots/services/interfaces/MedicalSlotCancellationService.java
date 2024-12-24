package com.api.v2.medical_slots.services.interfaces;

import reactor.core.publisher.Mono;

public interface MedicalSlotCancellationService {
    Mono<Void> cancel(String id);
}
