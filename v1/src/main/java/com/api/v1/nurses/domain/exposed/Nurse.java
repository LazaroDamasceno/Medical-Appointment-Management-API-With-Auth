package com.api.v1.nurses.domain.exposed;

import com.api.v1.common.ProfessionalStatus;
import com.api.v1.nurses.responses.NurseResponseDTO;
import com.api.v1.people.domain.exposed.Person;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "Nurses")
public class Nurse {

    @Id
    private String id;
    private Person person;
    private ProfessionalStatus status;
    private String licenseNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime terminatedAt;

    private Nurse() {

    }

    private Nurse(@NotNull Person person) {
        this.id = UUID.randomUUID().toString();
        this.person = person;
        this.status = ProfessionalStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    public static Nurse of(@NotNull Person person) {
        return new Nurse(person);
    }

    public void update(Person person) {
        this.person = person;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsTerminated() {
        this.status = ProfessionalStatus.TERMINATED;
        this.terminatedAt = LocalDateTime.now();
    }

    public void markAsRehired() {
        this.status = ProfessionalStatus.ACTIVE;
        this.terminatedAt = null;
    }

    public NurseResponseDTO toDTO() {
        return NurseResponseDTO.from(this);
    }

    public String getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public ProfessionalStatus getStatus() {
        return status;
    }

    public String getLicenseNumber() {
        return licenseNumber;
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
