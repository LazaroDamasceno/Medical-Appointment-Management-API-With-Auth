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

    private CanceledMedicalAppointmentResponseDto(MedicalAppointment medicalAppointment) {
        super(medicalAppointment);
        this.canceledAt = medicalAppointment.getCanceledAt();
        this.canceledAtZone = medicalAppointment.getCanceledAtZone();
    }

    public static CanceledMedicalAppointmentResponseDto create(MedicalAppointment medicalAppointment) {
        return new CanceledMedicalAppointmentResponseDto(medicalAppointment);
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public ZoneId getCanceledAtZone() {
        return canceledAtZone;
    }
}
