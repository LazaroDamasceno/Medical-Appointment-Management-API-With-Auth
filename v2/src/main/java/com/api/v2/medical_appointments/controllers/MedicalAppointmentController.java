package com.api.v2.medical_appointments.controllers;

import com.api.v2.medical_appointments.dtos.MedicalAppointmentBookingDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentBookingService;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentCancellationService;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentCompletionService;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentRetrievalService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/medical-appointments")
public class MedicalAppointmentController {

    private final MedicalAppointmentBookingService bookingService;
    private final MedicalAppointmentCancellationService cancellationService;
    private final MedicalAppointmentCompletionService completionService;
    private final MedicalAppointmentRetrievalService retrievalService;

    public MedicalAppointmentController(
            MedicalAppointmentBookingService bookingService,
            MedicalAppointmentCancellationService cancellationService,
            MedicalAppointmentCompletionService completionService,
            MedicalAppointmentRetrievalService retrievalService
    ) {
        this.bookingService = bookingService;
        this.cancellationService = cancellationService;
        this.completionService = completionService;
        this.retrievalService = retrievalService;
    }

    @PostMapping
    public Mono<MedicalAppointmentResponseDto> book(@Valid @RequestBody MedicalAppointmentBookingDto bookingDto) {
        return bookingService.book(bookingDto);
    }

    @PatchMapping("{id}/cancellation")
    public Mono<Void> cancel(@PathVariable String id) {
        return cancellationService.cancel(id);
    }

    @PatchMapping("{id}/completion")
    public Mono<Void> complete(@PathVariable String id) {
        return completionService.complete(id);
    }

    @GetMapping("{id}")
    public Mono<MedicalAppointmentResponseDto> findById(@PathVariable String id) {
        return retrievalService.findById(id);
    }

    @GetMapping
    public Flux<MedicalAppointmentResponseDto> findAll() {
        return retrievalService.findAll();
    }
}
