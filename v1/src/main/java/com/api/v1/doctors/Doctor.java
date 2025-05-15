package com.api.v1.doctors;

import com.api.v1.common.LicenseNumber;
import com.api.v1.common.ProfessionalStatus;
import com.api.v1.people.domain.exposed.Person;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Getter
public class Doctor {

    @Id
    private String id;
    private Person person;
    private LicenseNumber licenseNumber;
    private ProfessionalStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime terminatedAt;

    private Doctor() {
    }

    private Doctor(Person person,
                  LicenseNumber licenseNumber,
                  ProfessionalStatus status
    ) {
        this.id = UUID.randomUUID().toString();
        this.person = person;
        this.licenseNumber = licenseNumber;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public static Doctor of(Person person,
                         LicenseNumber licenseNumber,
                         ProfessionalStatus status
    ) {
        return new Doctor(person, licenseNumber, status);
    }

    public void update(Person person) {
        this.person = person;
        this.updatedAt = LocalDateTime.now();
    }

    public void markedAsTerminated() {
        this.terminatedAt = LocalDateTime.now();
        this.status = ProfessionalStatus.TERMINATED;
    }

    public void markedAsRehired() {
        this.terminatedAt = null;
        this.status = ProfessionalStatus.ACTIVE;
    }
}
