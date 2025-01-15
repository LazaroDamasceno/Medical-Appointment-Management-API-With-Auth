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
    private final MedicalAppointmentRetrievalService retrievalService;
    private final MedicalAppointmentCompletionService completionService;
    private final MedicalAppointmentCancellationService cancellationService;

    public MedicalAppointmentController(
            MedicalAppointmentBookingService bookingService,
            MedicalAppointmentRetrievalService retrievalService,
            MedicalAppointmentCompletionService completionService,
            MedicalAppointmentCancellationService cancellationService
    ) {
        this.bookingService = bookingService;
        this.retrievalService = retrievalService;
        this.completionService = completionService;
        this.cancellationService = cancellationService;
    }

    @PostMapping("public-insurance")
    public Mono<MedicalAppointmentResponseDto> publicInsuranceBooking(@Valid @RequestBody MedicalAppointmentBookingDto bookingDto) {
        return bookingService.publicInsuranceBook(bookingDto);
    }

    @PostMapping("private-insurance")
    public Mono<MedicalAppointmentResponseDto> privateInsuranceBooking(@Valid @RequestBody MedicalAppointmentBookingDto bookingDto) {
        return bookingService.privateInsuranceBook(bookingDto);
    }

    @PostMapping("private")
    public Mono<MedicalAppointmentResponseDto> privateBooking(@Valid @RequestBody MedicalAppointmentBookingDto bookingDto) {
        return bookingService.privateBook(bookingDto);
    }

    @GetMapping("{id}")
    public Mono<MedicalAppointmentResponseDto> findById(@PathVariable String id) {
        return retrievalService.findById(id);
    }

    @GetMapping
    public Flux<MedicalAppointmentResponseDto> findAll() {
        return retrievalService.findAll();
    }

    @GetMapping("public-insurance")
    public Flux<MedicalAppointmentResponseDto> findAllPublicInsurance() {
        return retrievalService.findAllPublicInsurance();
    }

    @GetMapping("private-insurance")
    public Flux<MedicalAppointmentResponseDto> findAllPrivateInsurance() {
        return retrievalService.findAllPrivateInsurance();
    }

    @GetMapping("paid-by-patient")
    public Flux<MedicalAppointmentResponseDto> findAllPaidByPatient() {
        return retrievalService.findAllPaidByPatient();
    }

    @PatchMapping("{id}/completion")
    public Mono<Void> complete(@PathVariable String id) {
        return completionService.complete(id);
    }

    @PatchMapping("{id}/cancellation")
    public Mono<Void> cancel(@PathVariable String id) {
        return cancellationService.cancel(id);
    }
}
