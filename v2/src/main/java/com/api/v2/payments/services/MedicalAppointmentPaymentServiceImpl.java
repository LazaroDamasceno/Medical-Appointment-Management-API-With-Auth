package com.api.v2.payments.services;

import com.api.v2.cards.domain.Card;
import com.api.v2.cards.utils.CardFinder;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.exceptions.ImmutableMedicalAppointmentException;
import com.api.v2.medical_appointments.utils.MedicalAppointmentFinder;
import com.api.v2.payments.domain.Payment;
import com.api.v2.payments.domain.PaymentRepository;
import com.api.v2.payments.dtos.PaymentResponseDto;
import com.api.v2.payments.utils.PaymentResponseMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class MedicalAppointmentPaymentServiceImpl implements MedicalAppointmentPaymentService {

    private final MedicalAppointmentFinder medicalAppointmentFinder;
    private final CardFinder cardFinder;
    private final PaymentRepository paymentRepository;
    private final MedicalAppointmentRepository medicalAppointmentRepository;

    public MedicalAppointmentPaymentServiceImpl(MedicalAppointmentFinder medicalAppointmentFinder,
                                                CardFinder cardFinder,
                                                PaymentRepository paymentRepository,
                                                MedicalAppointmentRepository medicalAppointmentRepository
    ) {
        this.medicalAppointmentFinder = medicalAppointmentFinder;
        this.cardFinder = cardFinder;
        this.paymentRepository = paymentRepository;
        this.medicalAppointmentRepository = medicalAppointmentRepository;
    }


    @Override
    public Mono<PaymentResponseDto> payPrivateInsurance(String medicalAppointmentId, String cardId, double price) {
        return pay(medicalAppointmentId, cardId, price);
    }

    @Override
    public Mono<PaymentResponseDto> payPaidByPatient(String medicalAppointmentId, String cardId, double price) {
        return pay(medicalAppointmentId, cardId, price);
    }

    private Mono<PaymentResponseDto> pay(String medicalAppointmentId, String cardId, double price) {
        Mono<MedicalAppointment> medicalAppointmentMono = medicalAppointmentFinder.findById(medicalAppointmentId);
        Mono<Card> cardMono = cardFinder.find(cardId);
        return medicalAppointmentMono
                .zipWith(cardMono)
                .flatMap(tuple -> {
            MedicalAppointment medicalAppointment = tuple.getT1();
            return onCanceledMedicalAppointment(medicalAppointment)
                    .then(onPaidMedicalAppointment(medicalAppointment))
                    .then(onActiveMedicalAppointment(medicalAppointment))
                    .then(Mono.defer(() -> {
                        Card card = tuple.getT2();
                        Payment payment = Payment.of(card, medicalAppointment, BigDecimal.valueOf(price));
                        medicalAppointment.markAsPaid();
                        return medicalAppointmentRepository
                                .save(medicalAppointment)
                                .then(Mono.defer(() -> {
                                    return paymentRepository
                                            .save(payment)
                                            .flatMap(PaymentResponseMapper::mapToMono);
                                }));
                    }));
        });
    }

    private Mono<Void> onCanceledMedicalAppointment(MedicalAppointment medicalAppointment) {
        if (medicalAppointment.getCompletedAt() != null && medicalAppointment.getCanceledAt() == null) {
            String message = "Medical appointment whose id is %s is already canceled.".formatted(medicalAppointment.getId());
            return Mono.error(new ImmutableMedicalAppointmentException(message));
        }
        return Mono.empty();
    }

    private Mono<Void> onActiveMedicalAppointment(MedicalAppointment medicalAppointment) {
        if (medicalAppointment.getCompletedAt() == null && medicalAppointment.getCanceledAt() == null) {
            String message = "Medical appointment whose id is %s is cannot be paid, due to it's active.".formatted(medicalAppointment.getId());
            return Mono.error(new ImmutableMedicalAppointmentException(message));
        }
        return Mono.empty();
    }

    private Mono<Void> onPaidMedicalAppointment(MedicalAppointment medicalAppointment) {
        if (medicalAppointment.getPaidAt()  != null) {
            String message = "Medical appointment whose id is %s is already completed.".formatted(medicalAppointment.getId());
            return Mono.error(new ImmutableMedicalAppointmentException(message));
        }
        return Mono.empty();
    }
}
