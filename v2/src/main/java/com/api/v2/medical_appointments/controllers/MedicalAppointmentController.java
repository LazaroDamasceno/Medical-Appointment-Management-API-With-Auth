package com.api.v2.medical_appointments.controllers;

import com.api.v2.medical_appointments.dtos.MedicalAppointmentBookingDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentBookingService;
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
    private final MedicalAppointmentRetrievalService retrievalService;
    private final MedicalAppointmentCompletionService completionService;

    public MedicalAppointmentController(
            MedicalAppointmentBookingService bookingService,
            MedicalAppointmentRetrievalService retrievalService,
            MedicalAppointmentCompletionService completionService
    ) {
        this.bookingService = bookingService;
        this.retrievalService = retrievalService;
        this.completionService = completionService;
    }

    @PostMapping
    public Mono<MedicalAppointmentResponseDto> book(@Valid @RequestBody MedicalAppointmentBookingDto bookingDto) {
        return bookingService.book(bookingDto);
    }

    @GetMapping("{id}")
    public Mono<MedicalAppointmentResponseDto> findById(@PathVariable String id) {
        return retrievalService.findById(id);
    }

    @GetMapping
    public Flux<MedicalAppointmentResponseDto> findAll() {
        return retrievalService.findAll();
    }

    @PatchMapping("{id}/completion")
    public Mono<Void> complete(@PathVariable String id) {
        return completionService.complete(id);
    }
}
