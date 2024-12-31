package com.api.v2.medical_slots.domain;

import com.api.v2.common.DataTimeAdapterUtil;
import com.api.v2.doctors.domain.Doctor;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Document
public class MedicalSlot {

    @BsonId
    private ObjectId id;
    private Doctor doctor;
    private LocalDateTime availableAt;
    private ZoneId availableAtZone;
    private MedicalAppointment medicalAppointment;
    private LocalDateTime createdAt;
    private ZoneId createdAtZone;
    private LocalDateTime canceledAt;
    private ZoneId canceledAtZone;
    private LocalDateTime completedAt;
    private ZoneId completedAtZone;

    private MedicalSlot(Doctor doctor, LocalDateTime availableAt) {
        this.id = new ObjectId();
        this.doctor = doctor;
        this.availableAt = DataTimeAdapterUtil.set(availableAt);
        this.availableAtZone = ZoneId.systemDefault();
        this.createdAt = DataTimeAdapterUtil.set(LocalDateTime.now());
        this.createdAtZone = ZoneId.systemDefault();
    }

    private MedicalSlot() {
    }

    public static MedicalSlot create(Doctor doctor, LocalDateTime availableAt) {
        return new MedicalSlot(doctor, availableAt);
    }

    public void markAsCanceled() {
        canceledAt = DataTimeAdapterUtil.set(LocalDateTime.now());
        canceledAtZone = ZoneId.systemDefault();
    }

    public void markAsCompleted() {
        completedAt = DataTimeAdapterUtil.set(LocalDateTime.now());
        completedAtZone = ZoneId.systemDefault();
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

    public ZoneId getAvailableAtZone() {
        return availableAtZone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ZoneId getCreatedAtZone() {
        return createdAtZone;
    }

    public MedicalAppointment getMedicalAppointment() {
        return medicalAppointment;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public ZoneId getCanceledAtZone() {
        return canceledAtZone;
    }

    public void setMedicalAppointment(MedicalAppointment medicalAppointment) {
        this.medicalAppointment = medicalAppointment;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public ZoneId getCompletedAtZone() {
        return completedAtZone;
    }
}
