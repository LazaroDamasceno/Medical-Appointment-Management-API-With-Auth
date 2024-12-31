package com.api.v2.doctors.domain;

import com.api.v2.common.DataTimeAdapterUtil;
import com.api.v2.people.domain.Person;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Document
public class Doctor {

    @BsonId
    private ObjectId  id;
    private String licenseNumber;
    private Person person;
    private LocalDateTime createdAt;
    private ZoneId createdAtZone;
    private LocalDateTime terminatedAt;
    private ZoneId terminatedAtZone;

    public Doctor() {
    }

    private Doctor(String licenseNumber, Person person) {
        this.id = new ObjectId();
        this.licenseNumber = licenseNumber;
        this.person = person;
        this.createdAt = DataTimeAdapterUtil.adapt(LocalDateTime.now());
        this.createdAtZone = ZoneId.systemDefault();
    }

    public static Doctor create(String licenseNumber, Person person) {
        return new Doctor(licenseNumber, person);
    }

    public void markAsTerminated() {
        terminatedAt = DataTimeAdapterUtil.adapt(LocalDateTime.now());
        terminatedAtZone = ZoneId.systemDefault();
    }

    public void markAsRehired() {
        terminatedAt = null;
        terminatedAtZone = null;
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

    public ZoneId getCreatedAtZone() {
        return createdAtZone;
    }

    public LocalDateTime getTerminatedAt() {
        return terminatedAt;
    }

    public ZoneId getTerminatedAtZone() {
        return terminatedAtZone;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
