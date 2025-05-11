package com.api.v1.doctors.responses;

import com.api.v1.common.ProfessionalStatus;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.people.utils.FullNameFormatter;
import org.springframework.hateoas.RepresentationModel;

public final class DoctorResponseDto extends RepresentationModel<DoctorResponseDto> {

    private String fullName;
    private ProfessionalStatus status;
    private String licenseNumber;

    DoctorResponseDto() {}

    private DoctorResponseDto(String fullName,
                              ProfessionalStatus status,
                              String licenseNumber
    ) {
        this.fullName = fullName;
        this.status = status;
        this.licenseNumber = licenseNumber;
    }

    public static DoctorResponseDto from(Doctor doctor) {
        return new DoctorResponseDto(
                FullNameFormatter.format(doctor.getPerson()),
                doctor.getStatus(),
                doctor.getLicenseNumber()
        );
    }

    public String getFullName() {
        return fullName;
    }

    public ProfessionalStatus getStatus() {
        return status;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }
}
