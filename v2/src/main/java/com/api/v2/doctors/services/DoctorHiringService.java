package com.api.v2.doctors.services;

import com.api.v2.doctors.dtos.DoctorHiringDto;
import com.api.v2.doctors.dtos.DoctorResponseDto;
import reactor.core.publisher.Mono;

public interface DoctorHiringService {
    Mono<DoctorResponseDto> hire(DoctorHiringDto hiringDto);
}
