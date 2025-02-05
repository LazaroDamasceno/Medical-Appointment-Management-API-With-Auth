package com.api.v2.medical_appointments.services.impl;

import com.api.v2.customers.domain.Customer;
import com.api.v2.customers.utils.CustomerFinderUtil;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.exceptions.ImmutableMedicalAppointmentException;
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
    private final CustomerFinderUtil customerFinderUtil;

    public MedicalAppointmentCancellationServiceImpl(
            MedicalSlotFinderUtil medicalSlotFinderUtil,
            MedicalAppointmentFinderUtil medicalAppointmentFinderUtil,
            MedicalSlotRepository medicalSlotRepository,
            MedicalAppointmentRepository medicalAppointmentRepository,
            CustomerFinderUtil customerFinderUtil
    ) {
        this.medicalSlotFinderUtil = medicalSlotFinderUtil;
        this.medicalAppointmentFinderUtil = medicalAppointmentFinderUtil;
        this.medicalSlotRepository = medicalSlotRepository;
        this.medicalAppointmentRepository = medicalAppointmentRepository;
        this.customerFinderUtil = customerFinderUtil;
    }

    @Override
    public Mono<Void> cancel(String customerId, String appointmentId) {
        return medicalAppointmentFinderUtil
                .findById(appointmentId)
                .flatMap(medicalAppointment -> {
                    return medicalSlotFinderUtil
                            .findByMedicalAppointment(medicalAppointment)
                            .flatMap(medicalSlot -> {
                                return customerFinderUtil
                                        .findById(customerId)
                                        .flatMap(customer -> {
                                            return onNonAssociatedMedicalAppointmentWithCustomer(medicalAppointment, customer)
                                                    .then(Mono.defer(() -> {
                                                        return onCanceledMedicalAppointment(medicalAppointment)
                                                                .then(onCompletedMedicalAppointment(medicalAppointment))
                                                                .then(Mono.defer(() -> {
                                                                    if (medicalSlot.getMedicalAppointment() == null) {
                                                                        return Mono.empty();
                                                                    }
                                                                    medicalSlot.setMedicalAppointment(null);
                                                                    return medicalSlotRepository.save(medicalSlot);
                                                                }))
                                                                .then(Mono.defer(() -> {
                                                                    medicalAppointment.markAsCanceled();
                                                                    return medicalAppointmentRepository.save(medicalAppointment);
                                                                }));
                                                    }));
                                        });
                            });
                })
                .then();
    }

    private Mono<Void> onNonAssociatedMedicalAppointmentWithCustomer(MedicalAppointment medicalAppointment, Customer customer) {
        if (medicalAppointment.getCustomer().getId().equals(customer.getId())) {
            String message = "Customer whose id is %s is not associated with the medical appointment whose id id %s"
                    .formatted(customer.getId().toString(), medicalAppointment.getId().toString());
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
