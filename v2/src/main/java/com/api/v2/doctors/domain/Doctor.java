package com.api.v2.doctors.domain;

import com.api.v2.common.DstCheckerUtil;
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
    private final String medicalLicenseNumber;
    private Person person;
    private final LocalDateTime createdAt;
    private final ZoneId createdAtZoneId;
    private final ZoneOffset createdAtZoneOffset;
    private final boolean isCreatedDuringDST;
    private LocalDateTime terminatedAt;
    private ZoneId terminatedAtZoneId;
    private ZoneOffset terminatedAtZoneOffset;
    private Boolean isTerminatedDuringDST;

    private Doctor(String medicalLicenseNumber, Person person) {
        this.id = new ObjectId();
        this.medicalLicenseNumber = medicalLicenseNumber;
        this.person = person;
        this.createdAt = LocalDateTime.now(ZoneId.systemDefault());
        this.createdAtZoneId = ZoneId.systemDefault();
        this.createdAtZoneOffset = OffsetDateTime.now().getOffset();
        this.isCreatedDuringDST = DstCheckerUtil.isGivenDateTimeFollowingDST(LocalDateTime.now(), ZoneId.systemDefault());
    }

    public static Doctor of(String licenseNumber, Person person) {
        return new Doctor(licenseNumber, person);
    }

    public void markAsTerminated() {
        terminatedAt = LocalDateTime.now(ZoneId.systemDefault());
        terminatedAtZoneId = ZoneId.systemDefault();
        terminatedAtZoneOffset = OffsetDateTime.now().getOffset();
        isTerminatedDuringDST = DstCheckerUtil.isGivenDateTimeFollowingDST(terminatedAt, createdAtZoneId);
    }

    public void markAsRehired() {
        terminatedAt = null;
        terminatedAtZoneId = null;
        terminatedAtZoneOffset = null;
        isTerminatedDuringDST = null;
    }

    public ObjectId getId() {
        return id;
    }

    public String getMedicalLicenseNumber() {
        return medicalLicenseNumber;
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

    public boolean isCreatedDuringDST() {
        return isCreatedDuringDST;
    }

    public boolean isTerminatedDuringDST() {
        return isTerminatedDuringDST;
    }
}
