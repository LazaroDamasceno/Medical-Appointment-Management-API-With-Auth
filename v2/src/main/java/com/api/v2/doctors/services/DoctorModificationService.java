package com.api.v2.doctors.services;

import com.api.v2.people.dtos.PersonModificationDto;
import reactor.core.publisher.Mono;

public interface DoctorModificationService {
    Mono<Void> modify(String medicalLicenseNumber, PersonModificationDto modificationDto);
}
