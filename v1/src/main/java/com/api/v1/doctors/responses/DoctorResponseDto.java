package com.api.v1.doctors.responses;

import com.api.v1.doctors.domain.MedicalLicenseNumber;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.enums.DoctorStatus;
import com.api.v1.people.utils.FullNameFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@NoArgsConstructor
public class DoctorResponseDto extends RepresentationModel<DoctorResponseDto> {

    private String fullName;
    private MedicalLicenseNumber medicalLicenseNumber;

    private DoctorResponseDto(String fullName,
                              MedicalLicenseNumber medicalLicenseNumber
    ) {
        this.fullName = fullName;
        this.medicalLicenseNumber = medicalLicenseNumber;
    }

    public static DoctorResponseDto from(Doctor doctor) {
        return new DoctorResponseDto(
                FullNameFormatter.format(doctor.getPerson()),
                doctor.getMedicalLicenseNumber()
        );
    }
}
