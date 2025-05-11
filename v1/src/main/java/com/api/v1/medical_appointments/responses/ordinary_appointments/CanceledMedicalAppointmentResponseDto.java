package com.api.v1.medical_appointments.responses.ordinary_appointments;

import com.api.v1.customers.responses.CustomerResponseDto;
import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.medical_appointments.domain.ordinaty_appointments.exposed.MedicalAppointment;
import com.api.v1.medical_appointments.enums.MedicalAppointmentStatus;

import java.time.LocalDateTime;

public final class CanceledMedicalAppointmentResponseDto extends MedicalAppointmentResponseDto {

    private LocalDateTime canceledAt;

    private CanceledMedicalAppointmentResponseDto() {}

    public CanceledMedicalAppointmentResponseDto(String id,
                                                 CustomerResponseDto customer,
                                                 DoctorResponseDto doctor,
                                                 MedicalAppointmentStatus status,
                                                 LocalDateTime bookedAt,
                                                 LocalDateTime canceledAt
    ) {
        super(id, customer, doctor, status, bookedAt);
        this.canceledAt = canceledAt;
    }

    public static CanceledMedicalAppointmentResponseDto from(MedicalAppointment medicalAppointment) {
        return new CanceledMedicalAppointmentResponseDto(
                medicalAppointment.getId(),
                medicalAppointment.getCustomer().toDto(),
                medicalAppointment.getDoctor().toDto(),
                medicalAppointment.getStatus(),
                medicalAppointment.getBookedAt(),
                medicalAppointment.getCanceledAt()
        );
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }
}
