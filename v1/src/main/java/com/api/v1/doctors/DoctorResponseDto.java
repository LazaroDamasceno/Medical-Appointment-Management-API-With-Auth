package com.api.v1.doctors;

import com.api.v1.people.FullNameFormatter;
import org.springframework.hateoas.RepresentationModel;

public final class DoctorResponseDto extends RepresentationModel<DoctorResponseDto> {

    private String fullName;
    private String licenseNumber;

    private DoctorResponseDto() {
    }

    private DoctorResponseDto(String fullName, String licenseNumber) {
        this.fullName = fullName;
        this.licenseNumber = licenseNumber;
    }

    public static DoctorResponseDto from(Doctor doctor) {
        return new DoctorResponseDto(
                FullNameFormatter.format(doctor.getPerson()),
                doctor.getLicenseNumber()
        );
    }

    public String getFullName() {
        return fullName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }
}
