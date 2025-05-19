package com.api.v1.doctors.domain.exposed;

import com.api.v1.common.ProfessionalStatus;
import com.api.v1.doctors.response.DoctorResponseDto;
import com.api.v1.people.domain.exposed.Person;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "Doctors")
public class Doctor {

    @Id
    private String id;
    private Person person;
    private String licenseNumber;
    private ProfessionalStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime terminatedAt;

    private Doctor() {
    }

    private Doctor(Person person,
                  String licenseNumber
    ) {
        this.id = UUID.randomUUID().toString();
        this.person = person;
        this.licenseNumber = licenseNumber;
        this.status = ProfessionalStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    public static Doctor of(Person person,
                         String licenseNumber
    ) {
        return new Doctor(person, licenseNumber);
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

    public DoctorResponseDto toDto() {
        return DoctorResponseDto.from(this);
    }

    public String getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public ProfessionalStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getTerminatedAt() {
        return terminatedAt;
    }
}
