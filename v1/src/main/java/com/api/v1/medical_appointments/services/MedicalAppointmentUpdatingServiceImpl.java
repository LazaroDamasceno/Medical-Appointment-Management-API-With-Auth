package com.api.v1.medical_appointments.services;

import com.api.v1.medical_appointments.domain.MedicalAppointmentAuditTrail;
import com.api.v1.medical_appointments.domain.MedicalAppointmentAuditTrailRepository;
import com.api.v1.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import com.api.v1.medical_appointments.services.exposed.MedicalAppointmentUpdatingService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MedicalAppointmentUpdatingServiceImpl implements MedicalAppointmentUpdatingService {

    private final MedicalAppointmentRepository appointmentRepository;
    private final MedicalAppointmentAuditTrailRepository auditTrailRepository;


    @Override
    public Mono<Void> update(@NotNull MedicalAppointment medicalAppointment) {
        return Mono.defer(() -> {
            MedicalAppointmentAuditTrail auditTrail = MedicalAppointmentAuditTrail.of(medicalAppointment);
            return auditTrailRepository
                    .save(auditTrail)
                    .flatMap(_ -> {
                        return appointmentRepository
                                .save(medicalAppointment)
                                .then();
                    });
        });
    }
}
