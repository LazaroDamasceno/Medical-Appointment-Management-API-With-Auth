package com.api.v2.medical_appointments.utils;

import com.api.v2.customers.utils.CustomerResponseMapper;
import com.api.v2.doctors.utils.DoctorResponseMapper;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;

public class MedicalAppointmentResponseMapper {

    public static MedicalAppointmentResponseDto mapToDto(MedicalAppointment medicalAppointment) {
        return new MedicalAppointmentResponseDto(
                CustomerResponseMapper.mapToDto(medicalAppointment.getCustomer()),
                DoctorResponseMapper.mapToDto(medicalAppointment.getDoctor()),
                medicalAppointment.getBookedAt(),
                medicalAppointment.getCanceledAt(),
                medicalAppointment.getCompletedAt()
        );
    }
}
