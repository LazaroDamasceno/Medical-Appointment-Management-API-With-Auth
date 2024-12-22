package com.api.v2.doctors.services;

import reactor.core.publisher.Mono;

public interface DoctorTerminationService {
    Mono<Void> terminate(String medicalLicenseNumber);
}
