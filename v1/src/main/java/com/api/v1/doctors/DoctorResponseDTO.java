package com.api.v1.doctors;

import com.api.v1.people.FullNameFormatter;
import org.springframework.hateoas.RepresentationModel;

public final class DoctorResponseDTO extends RepresentationModel<DoctorResponseDTO> {

    private String fullName;
    private String licenseNumber;

    private DoctorResponseDTO() {
    }

    private DoctorResponseDTO(String fullName, String licenseNumber) {
        this.fullName = fullName;
        this.licenseNumber = licenseNumber;
    }

    public static DoctorResponseDTO from(Doctor doctor) {
        return new DoctorResponseDTO(
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
