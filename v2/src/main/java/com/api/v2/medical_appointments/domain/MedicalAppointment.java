package com.api.v2.medical_appointments.domain;

import com.api.v2.customers.domain.Customer;
import com.api.v2.doctors.domain.Doctor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Document
public class MedicalAppointment {

    @BsonId
    private ObjectId id;
    private String type;
    private Customer customer;
    private Doctor doctor;
    private LocalDateTime bookedAt;
    private ZoneOffset bookedAtZone;
    private LocalDateTime canceledAt;
    private ZoneOffset canceledAtZone;
    private LocalDateTime completedAt;
    private ZoneOffset completedAtZone;
    private LocalDateTime createdAt;
    private ZoneOffset createdAtZone;

    public MedicalAppointment() {
    }

    private MedicalAppointment(String type, Customer customer, Doctor doctor, LocalDateTime bookedAt) {
        this.id = new ObjectId();
        this.customer = customer;
        this.doctor = doctor;
        this.bookedAt = bookedAt;
        this.bookedAtZone = OffsetDateTime.now().getOffset();
        this.createdAt = OffsetDateTime.now().toLocalDateTime();
        this.createdAtZone = OffsetDateTime.now().getOffset();
        this.type = type;
    }

    public static MedicalAppointment create(String type, Customer customer, Doctor doctor, LocalDateTime bookedAt) {
        return new MedicalAppointment(type, customer, doctor, bookedAt);
    }

    public void markAsCompleted() {
        completedAt = OffsetDateTime.now().toLocalDateTime();
        completedAtZone = OffsetDateTime.now().getOffset();
    }

    public void markAsCanceled() {
        canceledAt = OffsetDateTime.now().toLocalDateTime();
        canceledAtZone = OffsetDateTime.now().getOffset();
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

    public ZoneOffset getBookedAtZone() {
        return bookedAtZone;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public ZoneOffset getCanceledAtZone() {
        return canceledAtZone;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public ZoneOffset getCompletedAtZone() {
        return completedAtZone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ZoneOffset getCreatedAtZone() {
        return createdAtZone;
    }

    public String getType() {
        return type;
    }
}
