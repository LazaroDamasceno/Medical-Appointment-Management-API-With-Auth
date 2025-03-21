package com.api.v2.medical_appointments.services.impl;

import com.api.v2.doctors.domain.Doctor;
import com.api.v2.doctors.utils.DoctorFinder;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.exceptions.ImmutableMedicalAppointmentException;
import com.api.v2.medical_appointments.exceptions.InaccessibleMedicalAppointmentException;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentCompletionService;
import com.api.v2.medical_appointments.utils.MedicalAppointmentFinder;
import com.api.v2.medical_slots.services.interfaces.exposed.MedicalSlotCompletionService;
import com.api.v2.medical_slots.utils.MedicalSlotFinder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MedicalAppointmentCompletionServiceImpl implements MedicalAppointmentCompletionService {

    private final MedicalSlotFinder medicalSlotFinder;
    private final MedicalAppointmentFinder medicalAppointmentFinder;
    private final MedicalAppointmentRepository medicalAppointmentRepository;
    private final MedicalSlotCompletionService medicalSlotCompletionService;
    private final DoctorFinder doctorFinder;

    public MedicalAppointmentCompletionServiceImpl(
            MedicalSlotFinder medicalSlotFinder,
            MedicalAppointmentFinder medicalAppointmentFinder,
            MedicalAppointmentRepository medicalAppointmentRepository,
            MedicalSlotCompletionService medicalSlotCompletionService, DoctorFinder doctorFinder
    ) {
        this.medicalSlotFinder = medicalSlotFinder;
        this.medicalAppointmentFinder = medicalAppointmentFinder;
        this.medicalAppointmentRepository = medicalAppointmentRepository;
        this.medicalSlotCompletionService = medicalSlotCompletionService;
        this.doctorFinder = doctorFinder;
    }

    @Override
    public Mono<Void> complete(String medicalLicenseNumber, String appointmentId) {
        return medicalAppointmentFinder
                .findById(appointmentId)
                .flatMap(medicalAppointment -> {
                    return doctorFinder
                            .findByLicenseNumber(medicalLicenseNumber)
                            .flatMap(doctor -> {
                                return onNonAssociatedMedicalAppointmentWithCustomer(medicalAppointment, doctor)
                                        .then(Mono.defer(() -> {
                                            return onCanceledMedicalAppointment(medicalAppointment)
                                                    .then(onCompletedMedicalAppointment(medicalAppointment))
                                                    .then(medicalSlotFinder
                                                            .findByMedicalAppointment(medicalAppointment)
                                                            .flatMap(medicalSlot -> {
                                                                if (medicalSlot.getMedicalAppointment() == null) {
                                                                    return Mono.empty();
                                                                }
                                                                medicalAppointment.markAsCompleted();
                                                                return medicalSlotCompletionService
                                                                        .complete(medicalSlot, medicalAppointment)
                                                                        .then(medicalAppointmentRepository.save(medicalAppointment));
                                                            })
                                                    );
                                        }));
                            });
                })
                .then();
    }

    private Mono<Void> onNonAssociatedMedicalAppointmentWithCustomer(MedicalAppointment medicalAppointment, Doctor doctor) {
        if (!medicalAppointment.getDoctor().getId().equals(doctor.getId())) {
            String message = "Doctor whose medical license number is %s is not associated with the medical appointment whose id is %s"
                    .formatted(medicalAppointment.getId(), doctor.getId());
            return Mono.error(new InaccessibleMedicalAppointmentException(message));
        }
        return Mono.empty();
    }

    private Mono<Void> onCanceledMedicalAppointment(MedicalAppointment medicalAppointment) {
        if (medicalAppointment.getCompletedAt() != null && medicalAppointment.getCanceledAt() == null) {
            String message = "Medical appointment whose id is %s is already canceled.".formatted(medicalAppointment.getId());
            return Mono.error(new ImmutableMedicalAppointmentException(message));
        }
        return Mono.empty();
    }

    private Mono<Void> onCompletedMedicalAppointment(MedicalAppointment medicalAppointment) {
        if (medicalAppointment.getCompletedAt() == null && medicalAppointment.getCanceledAt() != null) {
            String message = "Medical appointment whose id is %s is already completed.".formatted(medicalAppointment.getId());
            return Mono.error(new ImmutableMedicalAppointmentException(message));
        }
        return Mono.empty();
    }
}
