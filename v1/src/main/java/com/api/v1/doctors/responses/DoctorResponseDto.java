package com.api.v1.doctors.responses;

import com.api.v1.doctors.dtos.MedicalLicenseNumber;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.people.utils.FullNameFormatter;
import org.springframework.hateoas.RepresentationModel;

public class DoctorResponseDto extends RepresentationModel<DoctorResponseDto> {

    private String fullName;
    private MedicalLicenseNumber licenseNumber;

    DoctorResponseDto() {}

    private DoctorResponseDto(String fullName,
                              MedicalLicenseNumber medicalLicenseNumber
    ) {
        this.fullName = fullName;
        this.licenseNumber = medicalLicenseNumber;
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

    public MedicalLicenseNumber getLicenseNumber() {
        return licenseNumber;
    }
}
