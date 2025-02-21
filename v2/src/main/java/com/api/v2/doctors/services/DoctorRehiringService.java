package com.api.v2.doctors.services;

import com.api.v2.common.MLN;

import reactor.core.publisher.Mono;

public interface DoctorRehiringService {
    Mono<Void> rehire(@MLN String medicalLicenseNumber);
}
