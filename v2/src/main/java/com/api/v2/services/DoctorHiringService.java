package com.api.v2.services;

import com.api.v2.dtos.DoctorHiringDto;
import com.api.v2.dtos.DoctorResponseDto;
import reactor.core.publisher.Mono;

public interface DoctorHiringService {
    Mono<DoctorResponseDto> hire(DoctorHiringDto hiringDto);
}
