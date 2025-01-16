package com.api.v2.medical_appointments.controllers;

import com.api.v2.medical_appointments.dtos.MedicalAppointmentBookingDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentBookingService;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentCancellationService;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentCompletionService;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentRetrievalService;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
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

    @PostMapping("/public-insurance")
    public Mono<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> publicInsuranceBook(@Valid @RequestBody MedicalAppointmentBookingDto bookingDto) {
        return bookingService.publicInsuranceBook(bookingDto);
    }

    @PostMapping("/private-insurance")
    public Mono<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> privateInsuranceBook(@Valid @RequestBody MedicalAppointmentBookingDto bookingDto) {
        return bookingService.privateInsuranceBook(bookingDto);
    }

    @PostMapping("/paid-by-patient")
    public Mono<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> paidByPatientBook(@Valid @RequestBody MedicalAppointmentBookingDto bookingDto) {
        return bookingService.paidByPatientBook(bookingDto);
    }

    @GetMapping("/{id}")
    public Mono<HalResourceWrapper<MedicalAppointmentResponseDto, Void>>findById(@PathVariable String id) {
        return retrievalService.findById(id);
    }

    @GetMapping
    public Flux<MedicalAppointmentResponseDto> findAll() {
        return retrievalService.findAll();
    }

    @GetMapping("/public-insurance/{ssn}")
    public Flux<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findAllPublicInsuranceByCustomer(@PathVariable String ssn) {
        return retrievalService.findAllPublicInsuranceByCustomer(ssn);
    }

    @GetMapping("/private-insurance/{ssn}")
    public Flux<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findAllPrivateInsuranceByCustomer(@PathVariable String ssn) {
        return retrievalService.findAllPrivateInsuranceByCustomer(ssn);
    }

    @GetMapping("/paid-by-patient/{ssn}")
    public Flux<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findAllPaidByPatientByCustomer(@PathVariable String ssn) {
        return retrievalService.findAllPaidByPatientByCustomer(ssn);
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
