package com.api.v2.medical_appointments.utils;

import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.dtos.CanceledCompleteMedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.dtos.CompletedCompleteMedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.dtos.CompleteMedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentWithoutCustomerAndDoctorResponseDto;
import reactor.core.publisher.Mono;

public class MedicalAppointmentResponseMapper {

    public static CompleteMedicalAppointmentResponseDto mapToDto(MedicalAppointment medicalAppointment) {
        if (medicalAppointment.getCanceledAt() != null) {
            return CanceledCompleteMedicalAppointmentResponseDto.from(medicalAppointment);
        }
        else if (medicalAppointment.getCompletedAt() != null) {
            CompletedCompleteMedicalAppointmentResponseDto.from(medicalAppointment);
        }
        return CompleteMedicalAppointmentResponseDto.from(medicalAppointment);
    }

    public static MedicalAppointmentWithoutCustomerAndDoctorResponseDto mapToDtoWithoutCustomerAndDoctor(MedicalAppointment medicalAppointment) {
       return MedicalAppointmentWithoutCustomerAndDoctorResponseDto.from(medicalAppointment);
    }

    public static Mono<CompleteMedicalAppointmentResponseDto> mapToMono(MedicalAppointment medicalAppointment) {
        return Mono.just(mapToDto(medicalAppointment));
    }
}
