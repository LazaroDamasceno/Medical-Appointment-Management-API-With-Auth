package com.api.v1.medical_appointments.services;

import com.api.v1.medical_appointments.domain.MedicalAppointmentAuditTrail;
import com.api.v1.medical_appointments.domain.MedicalAppointmentAuditTrailRepository;
import com.api.v1.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import com.api.v1.medical_appointments.services.exposed.MedicalAppointmentUpdatingService;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MedicalAppointmentUpdatingServiceImpl implements MedicalAppointmentUpdatingService {

    private final MedicalAppointmentRepository appointmentRepository;
    private final MedicalAppointmentAuditTrailRepository auditTrailRepository;

    public MedicalAppointmentUpdatingServiceImpl(MedicalAppointmentRepository appointmentRepository,
                                                 MedicalAppointmentAuditTrailRepository auditTrailRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.auditTrailRepository = auditTrailRepository;
    }

    @Override
    public Mono<MedicalAppointment>  update(@NotNull MedicalAppointment medicalAppointment) {
        return Mono.defer(() -> {
            MedicalAppointmentAuditTrail auditTrail = MedicalAppointmentAuditTrail.of(medicalAppointment);
            return auditTrailRepository
                    .save(auditTrail)
                    .flatMap(_ -> {
                        return appointmentRepository.save(medicalAppointment);
                    });
        });
    }
}
