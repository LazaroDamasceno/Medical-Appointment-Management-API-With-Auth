package com.api.v2.medical_slots.utils;

import com.api.v2.doctors.utils.DoctorResponseMapper;
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.dtos.*;
import reactor.core.publisher.Mono;

public class MedicalSlotResponseMapper {

    public static MedicalSlotResponseDto mapToDto(MedicalSlot medicalSlot) {
        if (medicalSlot.getMedicalAppointment() == null) {
            return new MedicalSlotResponseDto(
                    medicalSlot.getId(),
                    DoctorResponseMapper.mapToDto(medicalSlot.getDoctor()),
                    null,
                    "%s%s[%s]".formatted(
                            medicalSlot.getAvailableAt(),
                            medicalSlot.getAvailableAtZoneOffset(),
                            medicalSlot.getAvailableAtZoneId()
                    ),
                    null,
                    null
            );
        }
        if (medicalSlot.getCanceledAt() != null && medicalSlot.getMedicalAppointment() == null) {
            return new MedicalSlotResponseDto(
                    medicalSlot.getId(),
                    DoctorResponseMapper.mapToDto(medicalSlot.getDoctor()),
                    null,
                    "%s%s[%s]".formatted(
                            medicalSlot.getAvailableAt(),
                            medicalSlot.getAvailableAtZoneOffset(),
                            medicalSlot.getAvailableAtZoneId()
                    ),
                    "%s%s[%s]".formatted(
                            medicalSlot.getCanceledAt(),
                            medicalSlot.getCanceledAtZoneOffset(),
                            medicalSlot.getCanceledAtZoneId()
                    ),
                    null
            );
        }
        if (medicalSlot.getCanceledAt() != null && medicalSlot.getMedicalAppointment() != null) {
            return new MedicalSlotResponseDto(
                    medicalSlot.getId(),
                    DoctorResponseMapper.mapToDto(medicalSlot.getDoctor()),
                    MedicalAppointmentResponseMapper.mapToDto(medicalSlot.getMedicalAppointment()),
                    "%s%s[%s]".formatted(
                            medicalSlot.getAvailableAt(),
                            medicalSlot.getAvailableAtZoneOffset(),
                            medicalSlot.getAvailableAtZoneId()
                    ),
                    "%s%s[%s]".formatted(
                            medicalSlot.getCanceledAt(),
                            medicalSlot.getCanceledAtZoneOffset(),
                            medicalSlot.getCanceledAtZoneId()
                    ),
                    null
            );
        }
        if (medicalSlot.getCompletedAt() != null && medicalSlot.getMedicalAppointment() != null) {
            return new MedicalSlotResponseDto(
                    medicalSlot.getId(),
                    DoctorResponseMapper.mapToDto(medicalSlot.getDoctor()),
                    MedicalAppointmentResponseMapper.mapToDto(medicalSlot.getMedicalAppointment()),
                    "%s%s[%s]".formatted(
                            medicalSlot.getAvailableAt(),
                            medicalSlot.getAvailableAtZoneOffset(),
                            medicalSlot.getAvailableAtZoneId()
                    ),
                    null,
                    "%s%s[%s]".formatted(
                            medicalSlot.getCompletedAt(),
                            medicalSlot.getCompletedAtZoneOffset(),
                            medicalSlot.getCompletedAtZoneId()
                    )
            );
        }
        return new MedicalSlotResponseDto(
                medicalSlot.getId(),
                DoctorResponseMapper.mapToDto(medicalSlot.getDoctor()),
                MedicalAppointmentResponseMapper.mapToDto(medicalSlot.getMedicalAppointment()),
                "%s%s[%s]".formatted(
                        medicalSlot.getAvailableAt(),
                        medicalSlot.getAvailableAtZoneOffset(),
                        medicalSlot.getAvailableAtZoneId()
                ),
                null,
                null
        );
    }

    public static Mono<MedicalSlotResponseDto> mapToMono(MedicalSlot medicalSlot) {
        return Mono.just(mapToDto(medicalSlot));
    }
}
