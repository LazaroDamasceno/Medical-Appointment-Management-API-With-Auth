package com.api.v1.doctors.domain.exposed;

import com.api.v1.doctors.enums.DoctorStatus;
import com.api.v1.doctors.domain.MedicalLicenseNumber;
import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.people.domain.exposed.Person;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Doctor {

    @Id
    private String id;
    private MedicalLicenseNumber medicalLicenseNumber;
    private Person person;
    private DoctorStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime terminatedAt;

    private Doctor(MedicalLicenseNumber medicalLicenseNumber,
                  Person person
    ) {
        this.id = UUID.randomUUID().toString();
        this.medicalLicenseNumber = medicalLicenseNumber;
        this.person = person;
        this.createdAt = LocalDateTime.now();
        this.status = DoctorStatus.ACTIVE;
    }

    public static Doctor of(MedicalLicenseNumber medicalLicenseNumber, Person person) {
        return new Doctor(
                medicalLicenseNumber,
                person
        );
    }

    public DoctorResponseDto toDto() {
        return DoctorResponseDto.from(this);
    }

    public void markAsRehired() {
        terminatedAt = null;
        status = DoctorStatus.ACTIVE;
    }

    public void markAsTerminated() {
        terminatedAt = LocalDateTime.now();
        status = DoctorStatus.TERMINATED;
    }

    public void update(Person person) {
        this.person = person;
        updatedAt = LocalDateTime.now();
    }

}
