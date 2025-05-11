package com.api.v1.medical_appointments.controllers;

import com.api.v1.common.EmptyResponse;
import com.api.v1.medical_appointments.responses.MedicalAppointmentResponseDto;
import com.api.v1.medical_appointments.services.MedicalAppointmentCancellationService;
import com.api.v1.medical_appointments.services.MedicalAppointmentRegistrationService;
import com.api.v1.medical_appointments.services.MedicalAppointmentRetrievalService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/medical-appointments")
public class MedicalAppointmentController {

    private final MedicalAppointmentCancellationService cancellationService;
    private final MedicalAppointmentRegistrationService registrationService;
    private final MedicalAppointmentRetrievalService retrievalService;

    public MedicalAppointmentController(MedicalAppointmentCancellationService cancellationService,
                                        MedicalAppointmentRegistrationService registrationService,
                                        MedicalAppointmentRetrievalService retrievalService
    ) {
        this.cancellationService = cancellationService;
        this.registrationService = registrationService;
        this.retrievalService = retrievalService;
    }

    @PostMapping("{customerId}/{doctorLicenseNumber}/{bookedAt}")
    public Mono<ResponseEntity<MedicalAppointmentResponseDto>> register(@PathVariable String customerId,
                                                                        @PathVariable String doctorLicenseNumber,
                                                                        @PathVariable @NotNull LocalDateTime bookedAt) {
        return registrationService.register(customerId, doctorLicenseNumber, bookedAt);
    }

    @PatchMapping("{customerId}/{appointmentId}/cancellation")
    public Mono<ResponseEntity<EmptyResponse>>  cancel(@PathVariable String customerId, @PathVariable String appointmentId) {
        return cancellationService.cancel(customerId, appointmentId);
    }

    @GetMapping("{customerId}/{appointmentId}")
    public Mono<ResponseEntity<MedicalAppointmentResponseDto>> findById(@PathVariable String customerId,
                                                                        @PathVariable String appointmentId
    ) {
        return retrievalService.findById(customerId, appointmentId);
    }

    @GetMapping("{customerId}/{size}")
    public ResponseEntity<Flux<MedicalAppointmentResponseDto>> findAllByCustomer(@PathVariable String customerId,
                                                                                 @PathVariable long size
    ) {
        return retrievalService.findAllByCustomer(customerId, size);
    }

    @GetMapping("{doctorLicenseNumber}/{size}")
    public ResponseEntity<Flux<MedicalAppointmentResponseDto>> findAllByDoctor(@PathVariable String doctorLicenseNumber,
                                                                               @PathVariable long size
    ) {
        return retrievalService.findAllByDoctor(doctorLicenseNumber, size);
    }

    @GetMapping
    public ResponseEntity<Flux<MedicalAppointmentResponseDto>> findAll() {
        return retrievalService.findAll();
    }
}
