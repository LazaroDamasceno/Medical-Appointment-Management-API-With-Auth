package com.api.v2.doctors.utils;

import com.api.v2.doctors.domain.Doctor;
import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.people.utils.PersonResponseMapper;
import reactor.core.publisher.Mono;

public class DoctorResponseMapper {

    public static DoctorResponseDto mapToDto(Doctor doctor) {
        return new DoctorResponseDto(
                doctor.getLicenseNumber(),
                PersonResponseMapper.map(doctor.getPerson())
        );
    }

    public static Mono<DoctorResponseDto> mapToMono(Doctor doctor) {
        return Mono.just(mapToDto(doctor));
    }
}
