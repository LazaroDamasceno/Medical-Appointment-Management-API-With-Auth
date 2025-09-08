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
    @Indexed
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
