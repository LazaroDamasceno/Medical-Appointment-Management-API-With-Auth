package com.api.v2.medical_appointments.dtos;

import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.customers.utils.CustomerResponseMapper;
import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.doctors.utils.DoctorResponseMapper;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CanceledMedicalAppointmentResponseDto extends MedicalAppointmentResponseDto {

    private LocalDateTime canceledAt;
    private ZoneId canceledAtZone;

    public CanceledMedicalAppointmentResponseDto() {
    }

    private CanceledMedicalAppointmentResponseDto(
            ObjectId id,
            CustomerResponseDto customerResponseDto,
            DoctorResponseDto doctorResponseDto,
            String type,
            LocalDateTime bookedAt,
            ZoneId bookAtZone,
            LocalDateTime canceledAt,
            ZoneId canceledAtZone
    ) {
        super(id, customerResponseDto, doctorResponseDto, type, bookedAt, bookAtZone);
        this.canceledAt = canceledAt;
        this.canceledAtZone = canceledAtZone;
    }

    public static CanceledMedicalAppointmentResponseDto create(MedicalAppointment medicalAppointment) {
        return new CanceledMedicalAppointmentResponseDto(
                medicalAppointment.getId(),
                CustomerResponseMapper.mapToDto(medicalAppointment.getCustomer()),
                DoctorResponseMapper.mapToDto(medicalAppointment.getDoctor()),
                medicalAppointment.getType(),
                medicalAppointment.getBookedAt(),
                medicalAppointment.getBookedAtZone(),
                medicalAppointment.getCanceledAt(),
                medicalAppointment.getCanceledAtZone()
        );
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public ZoneId getCanceledAtZone() {
        return canceledAtZone;
    }
}
