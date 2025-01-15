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
    private ZoneId bookedAtZone;
    private LocalDateTime canceledAt;
    private ZoneId canceledAtZone;
    private LocalDateTime completedAt;
    private ZoneId completedAtZone;
    private LocalDateTime createdAt;
    private ZoneId createdAtZone;

    public MedicalAppointment() {
    }

    private MedicalAppointment(String type, Customer customer, Doctor doctor, LocalDateTime bookedAt) {
        this.id = new ObjectId();
        this.customer = customer;
        this.doctor = doctor;
        this.bookedAt = bookedAt;
        this.bookedAtZone = ZoneId.systemDefault();
        this.createdAt = LocalDateTime.now();
        this.createdAtZone = ZoneId.systemDefault();
    }

    public static MedicalAppointment create(String type, Customer customer, Doctor doctor, LocalDateTime bookedAt) {
        return new MedicalAppointment(type, customer, doctor, bookedAt);
    }

    public void markAsCompleted() {
        completedAt = LocalDateTime.now();
        completedAtZone = ZoneId.systemDefault();
    }

    public void markAsCanceled() {
        canceledAt = LocalDateTime.now();
        canceledAtZone = ZoneId.systemDefault();
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

    public ZoneId getBookedAtZone() {
        return bookedAtZone;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public ZoneId getCanceledAtZone() {
        return canceledAtZone;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public ZoneId getCompletedAtZone() {
        return completedAtZone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ZoneId getCreatedAtZone() {
        return createdAtZone;
    }

    public String getType() {
        return type;
    }
}
