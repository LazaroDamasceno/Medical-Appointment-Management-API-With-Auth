package com.api.v1.doctors;

import com.api.v1.common.ProfessionalStatus;
import com.api.v1.people.Person;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "Doctors")
public record Doctor(
        @Id
        String id,
        Person person,
        @Indexed(unique = true)
        String licenseNumber,
        ProfessionalStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime terminatedAt
) {

    public static Doctor of(Person person, String licenseNumber) {
        return new Doctor(
                UUID.randomUUID().toString(),
                person,
                licenseNumber,
                ProfessionalStatus.ACTIVE,
                LocalDateTime.now(),
                null,
                null
        );
    }

    public Doctor update(Person person) {
        return new Doctor(
                this.id,
                person,
                this.licenseNumber,
                this.status,
                this.createdAt,
                this.updatedAt,
                this.terminatedAt
        );
    }

    public Doctor markedAsTerminated() {
        return new Doctor(
                this.id,
                person,
                this.licenseNumber,
                ProfessionalStatus.TERMINATED,
                this.createdAt,
                this.updatedAt,
                LocalDateTime.now()
        );
    }

    public Doctor markedAsRehired() {
        return new Doctor(
                this.id,
                person,
                this.licenseNumber,
                ProfessionalStatus.ACTIVE,
                this.createdAt,
                this.updatedAt,
                null
        );
    }

    public DoctorResponseDTO toDTO() {
        return DoctorResponseDTO.from(this);
    }
}
