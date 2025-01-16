package com.api.v2.medical_appointments.dtos;

import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.customers.utils.CustomerResponseMapper;
import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.doctors.utils.DoctorResponseMapper;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CompletedMedicalAppointmentResponseDto extends MedicalAppointmentResponseDto {

    private LocalDateTime completedAt;
    private ZoneId completedAtZone;

    public CompletedMedicalAppointmentResponseDto() {
    }

    private CompletedMedicalAppointmentResponseDto(MedicalAppointment medicalAppointment) {
        super(medicalAppointment);
        this.completedAt = medicalAppointment.getCompletedAt();
        this.completedAtZone = medicalAppointment.getCompletedAtZone();
    }

    public static CompletedMedicalAppointmentResponseDto create(MedicalAppointment medicalAppointment) {
        return new CompletedMedicalAppointmentResponseDto(medicalAppointment);
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public ZoneId getCompletedAtZone() {
        return completedAtZone;
    }
}
