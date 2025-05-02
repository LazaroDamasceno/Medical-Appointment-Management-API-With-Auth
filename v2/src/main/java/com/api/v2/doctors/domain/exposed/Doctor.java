package com.api.v2.doctors.domain.exposed;

import com.api.v2.doctors.enums.DoctorStatus;
import com.api.v2.doctors.domain.MedicalLicenseNumber;
import com.api.v2.people.domain.exposed.Person;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

public record Doctor(
        @Id
        String id,
        MedicalLicenseNumber medicalLicenseNumber,
        Person person,
        LocalDateTime createdAAt,
        DoctorStatus status,
        LocalDateTime modifiedAt
) {

    public static Doctor of(MedicalLicenseNumber medicalLicenseNumber, Person person) {
        return new Doctor(
                UUID.randomUUID().toString(),
                medicalLicenseNumber,
                person,
                LocalDateTime.now(),
                DoctorStatus.ACTIVE,
                null
        );
    }

}
