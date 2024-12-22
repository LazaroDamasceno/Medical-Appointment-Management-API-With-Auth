package com.api.v2.doctors.services;

import reactor.core.publisher.Mono;

public interface DoctorModificationService {
    Mono<Void> modify(String medicalLicenseNumber);
}
