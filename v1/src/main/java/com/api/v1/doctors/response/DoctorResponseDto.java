package com.api.v1.doctors.response;

import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.people.utils.FullNameFormatter;
import org.springframework.hateoas.RepresentationModel;

public class DoctorResponseDto extends RepresentationModel<DoctorResponseDto> {

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
                FullNameFormatter.format(doctor.person()),
                doctor.licenseNumber()
        );
    }

    public String fullName() {
        return fullName;
    }

    public String licenseNumber() {
        return licenseNumber;
    }
}
