package com.api.v2.medical_appointments.domain;

import com.api.v2.common.DstCheckerUtil;
import com.api.v2.customers.domain.Customer;
import com.api.v2.doctors.domain.Doctor;
import com.api.v2.medical_appointments.MedicalAppointmentType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.UUID;

@Document
public class MedicalAppointment {

    @Id
    private String id;
    private final MedicalAppointmentType type;
    private final Customer customer;
    private final Doctor doctor;
    private final LocalDateTime bookedAt;
    private final ZoneId bookedAtZoneId;
    private final ZoneId bookedAtZoneOffset;
    private final boolean isBookedDuringDST;
    private LocalDateTime canceledAt;
    private ZoneId canceledAtZoneId;
    private ZoneOffset canceledAtZoneOffset;
    private Boolean isCanceledDuringDST;
    private LocalDateTime completedAt;
    private ZoneId completedAtZoneId;
    private ZoneOffset completedAtZoneOffset;
    private Boolean isCompletionDuringDST;
    private final LocalDateTime createdAt;
    private final ZoneId createdAtZoneId;
    private final ZoneOffset createdAtZoneOffset;
    private final boolean isCreatedDuringDST;

    private MedicalAppointment(MedicalAppointmentType type, Customer customer, Doctor doctor, LocalDateTime bookedAt) {
        this.id = UUID.randomUUID().toString();
        this.customer = customer;
        this.doctor = doctor;
        this.bookedAt = bookedAt;
        this.bookedAtZoneId = ZoneId.systemDefault();
        this.isBookedDuringDST = DstCheckerUtil.isGivenDateTimeFollowingDST(bookedAt, ZoneOffset.UTC);
        this.createdAt = LocalDateTime.now(ZoneId.systemDefault());
        this.createdAtZoneId = ZoneId.systemDefault();
        this.createdAtZoneOffset = OffsetDateTime.now().getOffset();
        this.isCreatedDuringDST = DstCheckerUtil.isGivenDateTimeFollowingDST(LocalDateTime.now(), ZoneId.systemDefault());
        this.bookedAtZoneOffset = OffsetDateTime
                .ofInstant(bookedAt.toInstant(ZoneOffset.UTC), ZoneId.systemDefault())
                .getOffset();
        this.type = type;
    }

    public static MedicalAppointment of(MedicalAppointmentType type, Customer customer, Doctor doctor, LocalDateTime bookedAt) {
        return new MedicalAppointment(type, customer, doctor, bookedAt);
    }

    public void markAsCompleted() {
        completedAt = LocalDateTime.now(ZoneId.systemDefault());
        completedAtZoneId = ZoneId.systemDefault();
        completedAtZoneOffset = OffsetDateTime.now().getOffset();
        isCompletionDuringDST = DstCheckerUtil.isGivenDateTimeFollowingDST(completedAt, completedAtZoneId);
    }

    public void markAsCanceled() {
        canceledAt = LocalDateTime.now(ZoneId.systemDefault());
        canceledAtZoneId = ZoneId.systemDefault();
        canceledAtZoneOffset = OffsetDateTime.now().getOffset();
        isCanceledDuringDST = DstCheckerUtil.isGivenDateTimeFollowingDST(canceledAt, canceledAtZoneId);
    }

    public String getId() {
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

    public MedicalAppointmentType getType() {
        return type;
    }

    public ZoneOffset getCreatedAtZoneOffset() {
        return createdAtZoneOffset;
    }

    public ZoneOffset getCanceledAtZoneOffset() {
        return canceledAtZoneOffset;
    }

    public ZoneOffset getCompletedAtZoneOffset() {
        return completedAtZoneOffset;
    }

    public ZoneId getBookedAtZoneOffset() {
        return bookedAtZoneOffset;
    }

    public boolean isBookedDuringDST() {
        return isBookedDuringDST;
    }

    public boolean isCanceledDuringDST() {
        return isCanceledDuringDST;
    }

    public boolean isCompletionDuringDST() {
        return isCompletionDuringDST;
    }

    public boolean isCreatedDuringDST() {
        return isCreatedDuringDST;
    }
}
