package com.api.v1.medical_appointments.responses;

import com.api.v1.customers.responses.CustomerResponseDto;
import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import com.api.v1.medical_appointments.enums.MedicalAppointmentStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MedicalAppointmentResponseDto {

    private String id;
    private CustomerResponseDto customer;
    private DoctorResponseDto doctor;
    private MedicalAppointmentStatus status;
    private LocalDateTime bookedAt;

    protected MedicalAppointmentResponseDto(String id,
                                          CustomerResponseDto customer,
                                          DoctorResponseDto doctor,
                                          MedicalAppointmentStatus status,
                                          LocalDateTime bookedAt
    ) {
        this.id = id;
        this.customer = customer;
        this.doctor = doctor;
        this.status = status;
        this.bookedAt = bookedAt;
    }

    public static MedicalAppointmentResponseDto from(MedicalAppointment medicalAppointment) {
        return new MedicalAppointmentResponseDto(
                medicalAppointment.getId(),
                medicalAppointment.getCustomer().toDto(),
                medicalAppointment.getDoctor().toDto(),
                medicalAppointment.getStatus(),
                medicalAppointment.getBookedAt()
        );
    }
}
