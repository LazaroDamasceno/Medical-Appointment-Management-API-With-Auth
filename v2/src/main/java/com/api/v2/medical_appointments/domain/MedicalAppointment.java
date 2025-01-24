package com.api.v2.medical_appointments.domain;

import com.api.v2.customers.domain.Customer;
import com.api.v2.doctors.domain.Doctor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Document
public class MedicalAppointment {

    @BsonId
    private ObjectId id;
    private String type;
    private Customer customer;
    private Doctor doctor;
    private LocalDateTime bookedAt;
    private ZoneId bookedAtZoneId;
    private LocalDateTime canceledAt;
    private ZoneId canceledAtZoneId;
    private LocalDateTime completedAt;
    private ZoneId completedAtZoneId;
    private LocalDateTime createdAt;
    private ZoneId createdAtZoneId;

    public MedicalAppointment() {
    }

    private MedicalAppointment(String type, Customer customer, Doctor doctor, LocalDateTime bookedAt) {
        this.id = new ObjectId();
        this.customer = customer;
        this.doctor = doctor;
        this.bookedAt = bookedAt;
        this.bookedAtZoneId = ZoneId.systemDefault();
        this.createdAt = LocalDateTime.now(ZoneId.systemDefault());
        this.createdAtZoneId = ZoneId.systemDefault();
        this.type = type;
    }

    public static MedicalAppointment create(String type, Customer customer, Doctor doctor, LocalDateTime bookedAt) {
        return new MedicalAppointment(type, customer, doctor, bookedAt);
    }

    public void markAsCompleted() {
        completedAt = LocalDateTime.now(ZoneId.systemDefault());
        completedAtZoneId = ZoneId.systemDefault();
    }

    public void markAsCanceled() {
        canceledAt = LocalDateTime.now(ZoneId.systemDefault());
        canceledAtZoneId = ZoneId.systemDefault();
    }

    public ObjectId getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public ZoneId getBookedAtZoneId() {
        return bookedAtZoneId;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public ZoneId getCanceledAtZoneId() {
        return canceledAtZoneId;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public ZoneId getCompletedAtZoneId() {
        return completedAtZoneId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ZoneId getCreatedAtZoneId() {
        return createdAtZoneId;
    }

    public String getType() {
        return type;
    }
}
