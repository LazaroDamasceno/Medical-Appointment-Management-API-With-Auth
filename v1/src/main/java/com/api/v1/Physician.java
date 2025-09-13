package com.api.v1;

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
    private MedicalLicenseNumber licenseNumber;
    private Person person;
    private boolean isLicenseValid;
    private MedicalSpecialization specialization;

    public Physician() {
    }

    private Physician(
            MedicalLicenseNumber licenseNumber,
            Person person,
            MedicalSpecialization specialization
    ) {
        this.id = UUID.randomUUID().toString();
        this.licenseNumber = licenseNumber;
        this.person = person;
        this.isLicenseValid = true;
        this.specialization = specialization;
    }

    public static Physician of(PhysicianRegistrationDto dto) {
        return new Physician(
                dto.licenseNumber(),
                dto.person(),
                dto.specialization()
        );
    }

    public void setValidLicense() {
        this.isLicenseValid = true;
    }

    public void setInvalidLicense() {
        this.isLicenseValid = false;
    }
}
