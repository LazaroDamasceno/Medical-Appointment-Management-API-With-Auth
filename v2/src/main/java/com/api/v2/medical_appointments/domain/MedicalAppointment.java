package com.api.v2.medical_appointments.domain;

import com.api.v2.customers.domain.Customer;
import com.api.v2.doctors.domain.Doctor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Document
public class MedicalAppointment {

    @BsonId
    private ObjectId id;
    private Customer customer;
    private Doctor doctor;
    private LocalDateTime bookedAt;
    private ZoneId bookedAtZone;
    private LocalDate canceledAt;
    private ZoneId canceledAtZone;
    private LocalDate completedAt;
    private ZoneId completedAtZone;
    private LocalDate createdAt;
    private ZoneId createdAtZone;

    public MedicalAppointment() {
    }

    private MedicalAppointment(Customer customer, Doctor doctor, LocalDateTime bookedAt) {
        this.id = new ObjectId();
        this.customer = customer;
        this.doctor = doctor;
        this.bookedAt = bookedAt;
        this.bookedAtZone = ZoneId.systemDefault();
        this.createdAt = LocalDate.now();
        this.createdAtZone = ZoneId.systemDefault();
    }

    public void markAsCompleted() {
        completedAt = LocalDate.now();
        completedAtZone = ZoneId.systemDefault();
    }

    public void markAsCanceled() {
        canceledAt = LocalDate.now();
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

    public LocalDate getCanceledAt() {
        return canceledAt;
    }

    public ZoneId getCanceledAtZone() {
        return canceledAtZone;
    }

    public LocalDate getCompletedAt() {
        return completedAt;
    }

    public ZoneId getCompletedAtZone() {
        return completedAtZone;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public ZoneId getCreatedAtZone() {
        return createdAtZone;
    }
}
