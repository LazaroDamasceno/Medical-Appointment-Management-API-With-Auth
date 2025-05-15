package com.api.v1.doctors;

import com.api.v1.common.LicenseNumber;
import com.api.v1.people.utils.FullNameFormatter;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class DoctorResponseDto extends RepresentationModel<DoctorResponseDto> {

    private String fullName;
    private LicenseNumber licenseNumber;

    private DoctorResponseDto() {
    }

    private DoctorResponseDto(String fullName, LicenseNumber licenseNumber) {
        this.fullName = fullName;
        this.licenseNumber = licenseNumber;
    }

    public static DoctorResponseDto from(Doctor doctor) {
        return new DoctorResponseDto(
                FullNameFormatter.format(doctor.getPerson()),
                doctor.getLicenseNumber()
        );
    }
}
