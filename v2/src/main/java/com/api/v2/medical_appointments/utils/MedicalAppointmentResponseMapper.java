package com.api.v2.medical_appointments.utils;

import com.api.v2.customers.utils.CustomerResponseMapper;
import com.api.v2.doctors.utils.DoctorResponseMapper;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.dtos.*;
import reactor.core.publisher.Mono;

public class MedicalAppointmentResponseMapper {

    public static MedicalAppointmentResponseDto mapToDto(MedicalAppointment medicalAppointment) {
        if (medicalAppointment.getCanceledAt() != null) {
            return new MedicalAppointmentResponseDto(
                    medicalAppointment.getId(),
                    CustomerResponseMapper.mapToDto(medicalAppointment.getCustomer()),
                    DoctorResponseMapper.mapToDto(medicalAppointment.getDoctor()),
                    medicalAppointment.getType(),
                    "%s%s[%s]".formatted(
                            medicalAppointment.getBookedAt(),
                            medicalAppointment.getBookedAtZoneOffset(),
                            medicalAppointment.getBookedAtZoneId()
                    ),
                    "%s%s[%s]".formatted(
                            medicalAppointment.getCanceledAt(),
                            medicalAppointment.getCanceledAtZoneOffset(),
                            medicalAppointment.getCanceledAtZoneId()
                    ),
                    null
            );
        }
        if (medicalAppointment.getCompletedAt() != null) {
            return new MedicalAppointmentResponseDto(
                    medicalAppointment.getId(),
                    CustomerResponseMapper.mapToDto(medicalAppointment.getCustomer()),
                    DoctorResponseMapper.mapToDto(medicalAppointment.getDoctor()),
                    medicalAppointment.getType(),
                    "%s%s[%s]".formatted(
                            medicalAppointment.getBookedAt(),
                            medicalAppointment.getBookedAtZoneOffset(),
                            medicalAppointment.getBookedAtZoneId()
                    ),
                    null,
                    "%s%s[%s]".formatted(
                            medicalAppointment.getCompletedAt(),
                            medicalAppointment.getCompletedAtZoneOffset(),
                            medicalAppointment.getCompletedAtZoneId()
                    )
            );
        }
        return new MedicalAppointmentResponseDto(
                medicalAppointment.getId(),
                CustomerResponseMapper.mapToDto(medicalAppointment.getCustomer()),
                DoctorResponseMapper.mapToDto(medicalAppointment.getDoctor()),
                medicalAppointment.getType(),
                "%s%s[%s]".formatted(
                        medicalAppointment.getBookedAt(),
                        medicalAppointment.getBookedAtZoneOffset(),
                        medicalAppointment.getBookedAtZoneId()
                ),
                null,
                null
        );
    }

    public static Mono<MedicalAppointmentResponseDto> mapToMono(MedicalAppointment medicalAppointment) {
        return Mono.just(mapToDto(medicalAppointment));
    }
}
