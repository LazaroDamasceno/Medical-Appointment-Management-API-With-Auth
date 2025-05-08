package com.api.v1.medical_appointments.responses;

import com.api.v1.customers.responses.CustomerResponseDto;
import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import com.api.v1.medical_appointments.enums.MedicalAppointmentStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CompletedMedicalAppointmentResponseDto extends MedicalAppointmentResponseDto {

    private LocalDateTime completedAt;

    public CompletedMedicalAppointmentResponseDto(String id,
                                                  CustomerResponseDto customer,
                                                  DoctorResponseDto doctor,
                                                  MedicalAppointmentStatus status,
                                                  LocalDateTime bookedAt,
                                                  LocalDateTime completedAt
    ) {
        super(id, customer, doctor, status, bookedAt);
        this.completedAt = completedAt;
    }

    public static CompletedMedicalAppointmentResponseDto from(MedicalAppointment medicalAppointment) {
        return new CompletedMedicalAppointmentResponseDto(
                medicalAppointment.getId(),
                medicalAppointment.getCustomer().toDto(),
                medicalAppointment.getDoctor().toDto(),
                medicalAppointment.getStatus(),
                medicalAppointment.getBookedAt(),
                medicalAppointment.getCompletedAt()
        );
    }
}
