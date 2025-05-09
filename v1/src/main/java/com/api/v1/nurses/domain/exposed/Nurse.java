package com.api.v1.nurses.domain.exposed;

import com.api.v1.common.ProfessionalStatus;
import com.api.v1.nurses.dtos.NurseLicenseNumber;
import com.api.v1.people.domain.exposed.Person;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
public class Nurse {

    @Id
    private String id;
    private Person person;
    private NurseLicenseNumber licenseNumber;
    private ProfessionalStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime terminatedAt;

    private Nurse() {}

    private Nurse(Person person, NurseLicenseNumber licenseNumber, ProfessionalStatus status) {
        this.id = UUID.randomUUID().toString();
        this.licenseNumber = licenseNumber;
        this.person = person;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public static Nurse of(Person person,
                           NurseLicenseNumber licenseNumber,
                           ProfessionalStatus status
    ) {
        return new Nurse(person, licenseNumber, status);
    }

    void markAsTerminated() {
        status = ProfessionalStatus.TERMINATED;
        terminatedAt = LocalDateTime.now();
    }

    void markAsRehired() {
        status = ProfessionalStatus.ACTIVE;
        terminatedAt = null;
    }

    public String getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public NurseLicenseNumber getLicenseNumber() {
        return licenseNumber;
    }

    public ProfessionalStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getTerminatedAt() {
        return terminatedAt;
    }
}
