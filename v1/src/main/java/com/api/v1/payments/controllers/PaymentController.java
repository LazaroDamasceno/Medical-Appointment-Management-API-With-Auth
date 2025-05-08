package com.api.v1.payments.controllers;

import com.api.v1.payments.domain.Payment;
import com.api.v1.payments.services.MedicalAppointmentPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final MedicalAppointmentPaymentService paymentService;

    @PostMapping("{appointmentId}")
    public Mono<Payment> pay(@PathVariable String appointmentId) {
        return paymentService.pay(appointmentId);
    }
}
