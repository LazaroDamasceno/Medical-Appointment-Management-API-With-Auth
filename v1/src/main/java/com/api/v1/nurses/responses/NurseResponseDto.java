package com.api.v1.nurses.responses;

import com.api.v1.common.ProfessionalStatus;
import com.api.v1.nurses.domain.exposed.Nurse;
import com.api.v1.people.utils.FullNameFormatter;
import org.springframework.hateoas.RepresentationModel;

public class NurseResponseDto extends RepresentationModel<NurseResponseDto> {

    private String fullName;
    private ProfessionalStatus status;
    private String licenseNumber;

    private NurseResponseDto() {}

    private NurseResponseDto(String fullName,
                             ProfessionalStatus status,
                             String licenseNumber
    ) {
        this.fullName = fullName;
        this.status = status;
        this.licenseNumber = licenseNumber;
    }

    public static NurseResponseDto from(Nurse nurse) {
        return new NurseResponseDto(
                FullNameFormatter.format(nurse.getPerson()),
                nurse.getStatus(),
                nurse.getLicenseNumber()
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
