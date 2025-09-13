package com.api.v1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Document(collection = "Physicians")
public class Physician {

    @Id
    private String id;
    @Indexed(unique = true)
    private MedicalLicense medicalLicense;
    private Person person;
    private boolean isLicenseValid;
    private MedicalSpecialization specialization;

    public Physician() {
    }

    private Physician(
            MedicalLicense medicalLicense,
            Person person,
            MedicalSpecialization specialization
    ) {
        this.id = UUID.randomUUID().toString();
        this.medicalLicense = medicalLicense;
        this.person = person;
        this.isLicenseValid = true;
        this.specialization = specialization;
    }

    public static Physician of(@Valid PhysicianRegistrationDto dto, @NotNull Person person) {
        return new Physician(
                dto.licenseNumber(),
                person,
                dto.specialization()
        );
    }

    public PhysicianResponseDto toDto() {
        return PhysicianResponseDto.from(this);
    }

    public void setValidLicense() {
        this.isLicenseValid = true;
    }

    public void setInvalidLicense() {
        this.isLicenseValid = false;
    }


}
