package com.api.v1;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Physician {

    private String id;
    private String licenseNumber;
    private Person person;

    public Physician() {
    }

    private Physician(
            String licenseNumber,
            Person person
    ) {
        this.id = UUID.randomUUID().toString();
        this.licenseNumber = licenseNumber;
        this.person = person;
    }

    public static Physician of(PhysicianRegistrationDto dto) {
        return new Physician(
                dto.licenseNumber(),
                dto.person()
        );
    }
}
