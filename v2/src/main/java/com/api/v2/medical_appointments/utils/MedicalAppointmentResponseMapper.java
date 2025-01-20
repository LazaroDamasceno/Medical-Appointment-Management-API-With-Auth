package com.api.v2.medical_appointments.utils;

import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.dtos.CanceledMedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.dtos.CompletedMedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import reactor.core.publisher.Mono;

public class MedicalAppointmentResponseMapper {

    public static MedicalAppointmentResponseDto mapToDto(MedicalAppointment medicalAppointment) {
        if (medicalAppointment.getCanceledAt() != null) {
            return CanceledMedicalAppointmentResponseDto.create(medicalAppointment);
        }
        else if (medicalAppointment.getCompletedAt() != null) {
            CompletedMedicalAppointmentResponseDto.create(medicalAppointment);
        }
        return MedicalAppointmentResponseDto.create(medicalAppointment);
    }

    public static Mono<MedicalAppointmentResponseDto> mapToMono(MedicalAppointment medicalAppointment) {
        return Mono.just(mapToDto(medicalAppointment));
    }
}
