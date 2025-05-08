package com.api.v1.medical_appointments.utils;

import com.api.v1.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import com.api.v1.medical_appointments.exceptions.MedicalAppointmentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MedicalAppointmentFinder {

    private final MedicalAppointmentRepository medicalAppointmentRepository;

    public Mono<MedicalAppointment> findById(String appointmentId) {
        return medicalAppointmentRepository
                .findById(appointmentId)
                .switchIfEmpty(Mono.error(new MedicalAppointmentNotFoundException(appointmentId)));
    }

}
