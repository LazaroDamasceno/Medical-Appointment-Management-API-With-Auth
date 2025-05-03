package com.api.v1.doctors.domain.exposed;

import com.api.v1.doctors.enums.DoctorStatus;
import com.api.v1.doctors.domain.MedicalLicenseNumber;
import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.utils.FullNameFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Getter
@NoArgsConstructor
public final class Doctor {

    @Id
    private String id;
    private MedicalLicenseNumber medicalLicenseNumber;
    private Person person;
    private LocalDateTime createdAAt;
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
        this.createdAAt = LocalDateTime.now();
        this.status = DoctorStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    public static Doctor of(MedicalLicenseNumber medicalLicenseNumber, Person person) {
        return new Doctor(
                medicalLicenseNumber,
                person
        );
    }

    public DoctorResponseDto toDto() {
        return new DoctorResponseDto(
                FullNameFormatter.format(person),
                medicalLicenseNumber
        );
    }

    public void markAsRehired() {
        terminatedAt = null;
        status = DoctorStatus.ACTIVE;
    }

    public void markAsTerminated() {
        terminatedAt = LocalDateTime.now();
        status = DoctorStatus.TERMINATE;
    }

    public void update(Person person) {
        this.person = person;
        updatedAt = LocalDateTime.now();
    }

}
