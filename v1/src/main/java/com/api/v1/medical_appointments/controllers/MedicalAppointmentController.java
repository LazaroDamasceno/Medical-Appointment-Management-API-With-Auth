package com.api.v1.medical_appointments.controllers;

import com.api.v1.medical_appointments.responses.MedicalAppointmentResponseDto;
import com.api.v1.medical_appointments.services.MedicalAppointmentRegistrationService;
import com.api.v1.medical_appointments.services.MedicalAppointmentRetrievalService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/medical-appointments")
public class MedicalAppointmentController {

    private final MedicalAppointmentRegistrationService registrationService;
    private final MedicalAppointmentRetrievalService retrievalService;

    @PostMapping("{customerId}/{doctorId}/{bookedAt}")
    public Mono<ResponseEntity<MedicalAppointmentResponseDto>> register(@PathVariable String customerId,
                                                                        @PathVariable String doctorId,
                                                                        @PathVariable @NotNull LocalDateTime bookedAt) {
        return registrationService.register(customerId, doctorId, bookedAt);
    }

    @GetMapping("{customerId}/{appointmentId}")
    public Mono<ResponseEntity<MedicalAppointmentResponseDto>> findById(@PathVariable String customerId,
                                                                        @PathVariable String appointmentId
    ) {
        return retrievalService.findById(customerId, appointmentId);
    }

    @GetMapping("{customerId}")
    public ResponseEntity<Flux<MedicalAppointmentResponseDto>> findAllByCustomer(@PathVariable String customerId) {
        return retrievalService.findAllByCustomer(customerId);
    }

    @GetMapping("{doctorId}")
    public ResponseEntity<Flux<MedicalAppointmentResponseDto>> findAllByDoctor(@PathVariable String doctorId) {
        return retrievalService.findAllByDoctor(doctorId);
    }

    @GetMapping
    public ResponseEntity<Flux<MedicalAppointmentResponseDto>> findAll() {
        return retrievalService.findAll();
    }
}
