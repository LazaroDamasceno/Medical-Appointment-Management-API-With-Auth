package com.api.v2.medical_appointments.services.impl;

import com.api.v2.customers.domain.Customer;
import com.api.v2.customers.utils.CustomerFinder;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.exceptions.ImmutableMedicalAppointmentException;
import com.api.v2.medical_appointments.exceptions.InaccessibleMedicalAppointmentException;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentCancellationService;
import com.api.v2.medical_appointments.utils.MedicalAppointmentFinder;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.utils.MedicalSlotFinder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MedicalAppointmentCancellationServiceImpl implements MedicalAppointmentCancellationService {

    private final MedicalSlotFinder medicalSlotFinder;
    private final MedicalAppointmentFinder medicalAppointmentFinder;
    private final MedicalSlotRepository medicalSlotRepository;
    private final MedicalAppointmentRepository medicalAppointmentRepository;
    private final CustomerFinder customerFinder;

    public MedicalAppointmentCancellationServiceImpl(
            MedicalSlotFinder medicalSlotFinder,
            MedicalAppointmentFinder medicalAppointmentFinder,
            MedicalSlotRepository medicalSlotRepository,
            MedicalAppointmentRepository medicalAppointmentRepository,
            CustomerFinder customerFinder
    ) {
        this.medicalSlotFinder = medicalSlotFinder;
        this.medicalAppointmentFinder = medicalAppointmentFinder;
        this.medicalSlotRepository = medicalSlotRepository;
        this.medicalAppointmentRepository = medicalAppointmentRepository;
        this.customerFinder = customerFinder;
    }

    @Override
    public Mono<Void> cancel(String customerId, String appointmentId) {
        return medicalAppointmentFinder
                .findById(appointmentId)
                .flatMap(medicalAppointment -> {
                    return medicalSlotFinder
                            .findByMedicalAppointment(medicalAppointment)
                            .flatMap(medicalSlot -> {
                                return customerFinder
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
                    .formatted(customer.getId(), medicalAppointment.getId());
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
