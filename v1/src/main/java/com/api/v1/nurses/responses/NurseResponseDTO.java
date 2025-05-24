package com.api.v1.nurses.responses;

import com.api.v1.nurses.domain.exposed.Nurse;
import com.api.v1.people.utils.FullNameFormatter;
import org.springframework.hateoas.RepresentationModel;

public final class NurseResponseDTO extends RepresentationModel<NurseResponseDTO> {

    private String licenseNumber;
    private String fullName;

    private NurseResponseDTO() {
    }

    private NurseResponseDTO(String licenseNumber, String fullName) {
        this.licenseNumber = licenseNumber;
        this.fullName = fullName;
    }

    public static NurseResponseDTO from(Nurse nurse) {
        return new NurseResponseDTO(
                nurse.getLicenseNumber(),
                FullNameFormatter.format(nurse.getPerson())
        );
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getFullName() {
        return fullName;
    }
}
