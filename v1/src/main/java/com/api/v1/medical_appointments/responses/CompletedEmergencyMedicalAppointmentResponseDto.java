package com.api.v1.medical_appointments.responses;

import com.api.v1.customers.responses.CustomerResponseDto;
import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.medical_appointments.domain.exposed.EmergencyMedicalAppointment;
import com.api.v1.medical_appointments.enums.MedicalAppointmentStatus;

import java.time.LocalDateTime;

public final class CompletedEmergencyMedicalAppointmentResponseDto extends EmergencyMedicalAppointmentResponseDto {

    private LocalDateTime completedAt;

    private CompletedEmergencyMedicalAppointmentResponseDto() {}

    private CompletedEmergencyMedicalAppointmentResponseDto(String id,
                                                           CustomerResponseDto customer,
                                                           DoctorResponseDto doctor,
                                                           MedicalAppointmentStatus status,
                                                           LocalDateTime createdAt,
                                                           LocalDateTime completedAt
    ) {
        super(id, customer, doctor, status, createdAt);
        this.completedAt = completedAt;
    }

    public static CompletedEmergencyMedicalAppointmentResponseDto from(EmergencyMedicalAppointment appointment) {
        return new CompletedEmergencyMedicalAppointmentResponseDto(
                appointment.getId(),
                appointment.getCustomer().toDto(),
                appointment.getDoctor().toDto(),
                appointment.getStatus(),
                appointment.getCreatedAt(),
                appointment.getCompletedAt()
        );
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
}
