package com.api.v2.medical_appointments.services.impl;

import com.api.v2.doctors.exceptions.ImmutableDoctorException;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentCancellationService;
import com.api.v2.medical_appointments.utils.MedicalAppointmentFinderUtil;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class MedicalAppointmentCancellationServiceImpl implements MedicalAppointmentCancellationService {

    private final MedicalAppointmentFinderUtil medicalAppointmentFinderUtil;
    private final MedicalAppointmentRepository medicalAppointmentRepository;

    public MedicalAppointmentCancellationServiceImpl(
            MedicalAppointmentFinderUtil medicalAppointmentFinderUtil,
            MedicalAppointmentRepository medicalAppointmentRepository
    ) {
        this.medicalAppointmentFinderUtil = medicalAppointmentFinderUtil;
        this.medicalAppointmentRepository = medicalAppointmentRepository;
    }

    @Override
    public Mono<Void> cancel(String id) {
        return medicalAppointmentFinderUtil
                .findById(id)
                .flatMap(medicalAppointment -> {
                    return onCanceledMedicalAppointment(medicalAppointment)
                            .then(onCompletedMedicalAppointment(medicalAppointment))
                            .then(Mono.defer(() -> {
                                medicalAppointment.markAsCanceled();
                                return medicalAppointmentRepository.save(medicalAppointment);
                            }));
                })
                .then();
    }

    private Mono<Void> onCanceledMedicalAppointment(MedicalAppointment medicalAppointment) {
        return Mono.defer(() -> {
            if (medicalAppointment.getCanceledAt() != null && medicalAppointment.getCompletedAt() == null) {
                String message = "Medical appointment whose id is %s is already canceled.".formatted(medicalAppointment.getId());
                return Mono.error(new ImmutableDoctorException(message));
            }
            return Mono.empty();
        });
    }

    private Mono<Void> onCompletedMedicalAppointment(MedicalAppointment medicalAppointment) {
        return Mono.defer(() -> {
            if (medicalAppointment.getCanceledAt() == null && medicalAppointment.getCompletedAt() != null) {
                String message = "Medical appointment whose id is %s is already completed.".formatted(medicalAppointment.getId());
                return Mono.error(new ImmutableDoctorException(message));
            }
            return Mono.empty();
        });
    }
}
