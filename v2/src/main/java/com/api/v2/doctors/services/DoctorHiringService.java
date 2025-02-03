package com.api.v2.doctors.services;

import com.api.v2.doctors.dtos.DoctorHiringDto;
import com.api.v2.doctors.dtos.DoctorResponseDto;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import reactor.core.publisher.Mono;

public interface DoctorHiringService {
    Mono<HalResourceWrapper<DoctorResponseDto, Void>> hire(DoctorHiringDto hiringDto);
}
