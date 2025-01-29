package com.api.v2.doctors.domain;

import com.api.v2.people.domain.Person;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Document
public class  Doctor {

    @BsonId
    private ObjectId  id;
    private String licenseNumber;
    private Person person;
    private LocalDateTime createdAt;
    private ZoneId createdAtZoneId;
    private ZoneOffset createdAtZoneOffset;
    private LocalDateTime terminatedAt;
    private ZoneId terminatedAtZoneId;
    private ZoneOffset terminatedAtZoneOffset;

    public Doctor() {
    }

    private Doctor(String licenseNumber, Person person) {
        this.id = new ObjectId();
        this.licenseNumber = licenseNumber;
        this.person = person;
        this.createdAt = LocalDateTime.now(ZoneId.systemDefault());
        this.createdAtZoneId = ZoneId.systemDefault();
        this.createdAtZoneOffset = OffsetDateTime.now().getOffset();
    }

    public static Doctor create(String licenseNumber, Person person) {
        return new Doctor(licenseNumber, person);
    }

    public void markAsTerminated() {
        terminatedAt = LocalDateTime.now(ZoneId.systemDefault());
        terminatedAtZoneId = ZoneId.systemDefault();
        terminatedAtZoneOffset = OffsetDateTime.now().getOffset();
    }

    public void markAsRehired() {
        terminatedAt = null;
        terminatedAtZoneId = null;
        terminatedAtZoneOffset = null;
    }

    public ObjectId getId() {
        return id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public Person getPerson() {
        return person;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ZoneId getCreatedAtZoneId() {
        return createdAtZoneId;
    }

    public LocalDateTime getTerminatedAt() {
        return terminatedAt;
    }

    public ZoneId getTerminatedAtZoneId() {
        return terminatedAtZoneId;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ZoneOffset getCreatedAtZoneOffset() {
        return createdAtZoneOffset;
    }

    public ZoneOffset getTerminatedAtZoneOffset() {
        return terminatedAtZoneOffset;
    }
    
}
