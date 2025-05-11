package com.api.v1.payments.services;

import com.api.v1.medical_appointments.domain.ordinaty_appointments.exposed.MedicalAppointment;
import com.api.v1.medical_appointments.enums.MedicalAppointmentStatus;
import com.api.v1.medical_appointments.exceptions.ActiveMedicalAppointmentException;
import com.api.v1.medical_appointments.exceptions.CanceledMedicalAppointmentException;
import com.api.v1.medical_appointments.exceptions.PaidMedicalAppointmentException;
import com.api.v1.medical_appointments.services.ordinary_appointments.exposed.MedicalAppointmentUpdatingService;
import com.api.v1.medical_appointments.utils.MedicalAppointmentFinder;
import com.api.v1.medical_slots.services.exposed.MedicalSlotUpdatingService;
import com.api.v1.medical_slots.utils.MedicalSlotFinder;
import com.api.v1.payments.domain.Payment;
import com.api.v1.payments.domain.PaymentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MedicalAppointmentPaymentServiceImpl implements MedicalAppointmentPaymentService {

    private final PaymentRepository paymentRepository;
    private final MedicalAppointmentFinder appointmentFinder;
    private final MedicalAppointmentUpdatingService medicalAppointmentUpdatingService;
    private final MedicalSlotFinder medicalSlotFinder;
    private final MedicalSlotUpdatingService medicalSlotUpdatingService;

    public MedicalAppointmentPaymentServiceImpl(PaymentRepository paymentRepository,
                                                MedicalAppointmentFinder appointmentFinder,
                                                MedicalAppointmentUpdatingService medicalAppointmentUpdatingService,
                                                MedicalSlotFinder medicalSlotFinder,
                                                MedicalSlotUpdatingService medicalSlotUpdatingService
    ) {
        this.paymentRepository = paymentRepository;
        this.appointmentFinder = appointmentFinder;
        this.medicalAppointmentUpdatingService = medicalAppointmentUpdatingService;
        this.medicalSlotFinder = medicalSlotFinder;
        this.medicalSlotUpdatingService = medicalSlotUpdatingService;
    }

    @Override
    public Mono<Payment> pay(String appointmentId, double price) {
        return appointmentFinder
                .findById(appointmentId)
                .flatMap(foundAppointment -> {
                    return validate(foundAppointment)
                            .then(Mono.defer(() -> {
                                return medicalSlotFinder
                                        .findActiveOrCompletedByMedicalAppointment(foundAppointment.getId())
                                        .flatMap(foundSlot -> {
                                            foundAppointment.markAsPaid();
                                            return medicalAppointmentUpdatingService
                                                    .update(foundAppointment)
                                                    .flatMap(appointment -> {
                                                        foundSlot.setMedicalAppointment(appointment);
                                                        return medicalSlotUpdatingService
                                                                .update(foundSlot, appointment)
                                                                .flatMap(_ -> {
                                                                    Payment payment = Payment.of(appointment, price);
                                                                    return paymentRepository.save(payment);
                                                                });
                                                    });
                                        });
                            }));
                });
    }

    private Mono<Object> validate(MedicalAppointment medicalAppointment) {
        if (medicalAppointment.getStatus().equals(MedicalAppointmentStatus.ACTIVE)) {
            return Mono.error(new ActiveMedicalAppointmentException(medicalAppointment.getId()));
        }
        if (medicalAppointment.getStatus().equals(MedicalAppointmentStatus.CANCELED)) {
            return Mono.error(new CanceledMedicalAppointmentException(medicalAppointment.getId()));
        }
        else if (medicalAppointment.getStatus().equals(MedicalAppointmentStatus.PAID)) {
            return Mono.error(new PaidMedicalAppointmentException(medicalAppointment.getId()));
        }
        return Mono.empty();
    }
}
