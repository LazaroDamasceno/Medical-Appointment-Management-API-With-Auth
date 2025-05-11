package com.api.v1.medical_appointments.utils;

import com.api.v1.medical_appointments.domain.ordinaty_appointments.MedicalAppointmentRepository;
import com.api.v1.medical_appointments.domain.ordinaty_appointments.exposed.MedicalAppointment;
import com.api.v1.medical_appointments.exceptions.MedicalAppointmentNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public final class MedicalAppointmentFinder {

    private final MedicalAppointmentRepository medicalAppointmentRepository;

    public MedicalAppointmentFinder(MedicalAppointmentRepository medicalAppointmentRepository) {
        this.medicalAppointmentRepository = medicalAppointmentRepository;
    }

    public Mono<MedicalAppointment> findById(String appointmentId) {
        return medicalAppointmentRepository
                .findById(appointmentId)
                .switchIfEmpty(Mono.error(new MedicalAppointmentNotFoundException(appointmentId)));
    }

}
