package com.api.v1.payments.controllers;

import com.api.v1.payments.domain.Payment;
import com.api.v1.payments.services.MedicalAppointmentPaymentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/payments")
public class PaymentController {

    private final MedicalAppointmentPaymentService paymentService;

    public PaymentController(MedicalAppointmentPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("{appointmentId}")
    public Mono<Payment> pay(@PathVariable String appointmentId) {
        return paymentService.pay(appointmentId);
    }
}
