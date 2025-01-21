package com.api.v2.medical_appointments.utils;

import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.dtos.*;
import reactor.core.publisher.Mono;

public class MedicalAppointmentResponseMapper {

    public static MedicalAppointmentResponseDto mapToDto(MedicalAppointment medicalAppointment) {
        if (medicalAppointment.getCanceledAt() != null) {
            return CanceledMedicalAppointmentResponseDto.from(medicalAppointment);
        }
        else if (medicalAppointment.getCompletedAt() != null) {
            CompletedMedicalAppointmentResponseDto.from(medicalAppointment);
        }
        return MedicalAppointmentResponseDto.from(medicalAppointment);
    }

    public static MedicalAppointmentWithoutDoctorResponseDto mapToDtoWithoutDoctor(MedicalAppointment medicalAppointment) {
        if (medicalAppointment.getCompletedAt() != null) {
            return CompletedMedicalAppointmentWithoutDoctorResponseDto.from(medicalAppointment);
        }
        else if (medicalAppointment.getCanceledAt() != null) {
            return CanceledMedicalAppointmentWithoutDoctorResponseDto.from(medicalAppointment);
        }
        return MedicalAppointmentWithoutDoctorResponseDto.from(medicalAppointment);
    }

    public static Mono<MedicalAppointmentResponseDto> mapToMono(MedicalAppointment medicalAppointment) {
        return Mono.just(mapToDto(medicalAppointment));
    }
}
