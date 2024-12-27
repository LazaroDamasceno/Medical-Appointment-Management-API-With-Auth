package com.api.v2.medical_slots.services.interfaces;

import reactor.core.publisher.Mono;

public interface MedicalSlotCompletionService {
    Mono<Void> complete(String id);
}
