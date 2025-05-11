package com.api.v1.doctors.domain.exposed;

import com.api.v1.common.ProfessionalStatus;
import com.api.v1.doctors.enums.MedicalSpeciality;
import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.people.domain.exposed.Person;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
public class Doctor {

    @Id
    private String id;
    private String licenseNumber;
    private Person person;
    private MedicalSpeciality medicalSpeciality;
    private ProfessionalStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime terminatedAt;

    private Doctor() {
    }

    private Doctor(String licenseNumber,
                   Person person,
                   MedicalSpeciality medicalSpeciality
    ) {
        this.id = UUID.randomUUID().toString();
        this.licenseNumber = licenseNumber;
        this.person = person;
        this.medicalSpeciality = medicalSpeciality;
        this.createdAt = LocalDateTime.now();
        this.status = ProfessionalStatus.ACTIVE;
    }

    public static Doctor of(String licenseNumber, Person person, MedicalSpeciality medicalSpeciality) {
        return new Doctor(
                licenseNumber,
                person,
                medicalSpeciality
        );
    }

    public DoctorResponseDto toDto() {
        return DoctorResponseDto.from(this);
    }

    public void markAsRehired() {
        terminatedAt = null;
        status = ProfessionalStatus.ACTIVE;
    }

    public void markAsTerminated() {
        terminatedAt = LocalDateTime.now();
        status = ProfessionalStatus.TERMINATED;
    }

    public void update(Person person) {
        this.person = person;
        updatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public Person getPerson() {
        return person;
    }

    public MedicalSpeciality getMedicalSpeciality() {
        return medicalSpeciality;
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
