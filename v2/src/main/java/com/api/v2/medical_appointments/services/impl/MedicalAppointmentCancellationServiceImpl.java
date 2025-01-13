package com.api.v2.medical_appointments.services.impl;

import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentCancellationService;
import com.api.v2.medical_appointments.utils.MedicalAppointmentFinderUtil;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.utils.MedicalSlotFinderUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MedicalAppointmentCancellationServiceImpl implements MedicalAppointmentCancellationService {

    private final MedicalSlotFinderUtil medicalSlotFinderUtil;
    private final MedicalAppointmentFinderUtil medicalAppointmentFinderUtil;
    private final MedicalSlotRepository medicalSlotRepository;
    private final MedicalAppointmentRepository medicalAppointmentRepository;

    public MedicalAppointmentCancellationServiceImpl(
            MedicalSlotFinderUtil medicalSlotFinderUtil,
            MedicalAppointmentFinderUtil medicalAppointmentFinderUtil,
            MedicalSlotRepository medicalSlotRepository,
            MedicalAppointmentRepository medicalAppointmentRepository
    ) {
        this.medicalSlotFinderUtil = medicalSlotFinderUtil;
        this.medicalAppointmentFinderUtil = medicalAppointmentFinderUtil;
        this.medicalSlotRepository = medicalSlotRepository;
        this.medicalAppointmentRepository = medicalAppointmentRepository;
    }

    @Override
    public Mono<Void> cancel(String id) {
        return medicalAppointmentFinderUtil
                .findById(id)
                .flatMap(medicalAppointment -> {
                    return medicalSlotFinderUtil
                            .findByMedicalAppointment(medicalAppointment)
                            .flatMap(medicalSlot -> {
                                medicalSlot.markAsCanceled();
                                medicalSlot.setMedicalAppointment(null);
                                return medicalSlotRepository.save(medicalSlot);
                            })
                            .then(Mono.defer(() -> {
                                medicalAppointment.markAsCanceled();
                                return medicalAppointmentRepository.save(medicalAppointment);
                            }));
                })
                .then();
    }
}
