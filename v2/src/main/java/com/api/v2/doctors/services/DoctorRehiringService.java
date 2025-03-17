package com.api.v2.doctors.services;

import reactor.core.publisher.Mono;

public interface DoctorRehiringService {
    Mono<Void> rehire(String medicalLicenseNumber);
}
