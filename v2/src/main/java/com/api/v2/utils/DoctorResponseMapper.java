package com.api.v2.utils;

import com.api.v2.domain.Doctor;
import com.api.v2.dtos.DoctorResponseDto;

public class DoctorResponseMapper {

    public static DoctorResponseDto mapToDto(Doctor doctor) {
        return new DoctorResponseDto(
                doctor.getLicenseNumber(),
                PersonResponseMapper.map(doctor.getPerson())
        );
    }
}
