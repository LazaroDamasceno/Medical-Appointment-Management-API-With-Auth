package com.api.v2.medical_slots.domain;

import com.api.v2.doctors.domain.Doctor;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Document
public class MedicalSlot {

    @BsonId
    private ObjectId id;
    private Doctor doctor;
    private LocalDateTime availableAt;
    private ZoneOffset availableAtZone;
    private MedicalAppointment medicalAppointment;
    private LocalDateTime createdAt;
    private ZoneOffset createdAtZone;
    private LocalDateTime canceledAt;
    private ZoneOffset canceledAtZone;
    private LocalDateTime completedAt;
    private ZoneOffset completedAtZone;

    private MedicalSlot(Doctor doctor, LocalDateTime availableAt) {
        this.id = new ObjectId();
        this.doctor = doctor;
        this.availableAt = availableAt;
        this.availableAtZone = OffsetDateTime.now().getOffset();
        this.createdAt = OffsetDateTime.now().toLocalDateTime();
        this.createdAtZone = OffsetDateTime.now().getOffset();
    }

    public MedicalSlot() {
    }

    public static MedicalSlot create(Doctor doctor, LocalDateTime availableAt) {
        return new MedicalSlot(doctor, availableAt);
    }

    public void markAsCanceled() {
        canceledAt = OffsetDateTime.now().toLocalDateTime();
        canceledAtZone = OffsetDateTime.now().getOffset();
    }

    public void markAsCompleted() {
        completedAt = OffsetDateTime.now().toLocalDateTime();
        completedAtZone = OffsetDateTime.now().getOffset();
    }

    public ObjectId getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDateTime getAvailableAt() {
        return availableAt;
    }

    public ZoneOffset getAvailableAtZone() {
        return availableAtZone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ZoneOffset getCreatedAtZone() {
        return createdAtZone;
    }

    public MedicalAppointment getMedicalAppointment() {
        return medicalAppointment;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public ZoneOffset getCanceledAtZone() {
        return canceledAtZone;
    }

    public void setMedicalAppointment(MedicalAppointment medicalAppointment) {
        this.medicalAppointment = medicalAppointment;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public ZoneOffset getCompletedAtZone() {
        return completedAtZone;
    }
}
