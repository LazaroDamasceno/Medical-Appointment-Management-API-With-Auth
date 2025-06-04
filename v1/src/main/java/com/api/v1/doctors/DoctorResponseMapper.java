package com.api.v1.doctors;

import com.api.v1.people.FullNameFormatter;

public final class DoctorResponseMapper {

    public static DoctorResponseDTO from(Doctor doctor) {
        return new DoctorResponseDTO(
                FullNameFormatter.format(doctor.getPerson()),
                doctor.getLicenseNumber()
        );
    }
}
