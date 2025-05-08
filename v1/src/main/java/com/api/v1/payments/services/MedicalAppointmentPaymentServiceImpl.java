package com.api.v1.payments.services;

import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import com.api.v1.medical_appointments.enums.MedicalAppointmentStatus;
import com.api.v1.medical_appointments.exceptions.ActiveMedicalAppointmentException;
import com.api.v1.medical_appointments.exceptions.CanceledMedicalAppointmentException;
import com.api.v1.medical_appointments.exceptions.PaidMedicalAppointmentException;
import com.api.v1.medical_appointments.utils.MedicalAppointmentFinder;
import com.api.v1.payments.domain.Payment;
import com.api.v1.payments.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MedicalAppointmentPaymentServiceImpl implements MedicalAppointmentPaymentService {

    private final PaymentRepository paymentRepository;
    private final MedicalAppointmentFinder appointmentFinder;

    @Override
    public Mono<Payment> pay(String appointmentId) {
        return appointmentFinder
                .findById(appointmentId)
                .flatMap(foundAppointment -> {
                    return validate(foundAppointment)
                            .then(Mono.defer(() -> {
                                Payment payment = Payment.of(foundAppointment);
                                return paymentRepository.save(payment);
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
