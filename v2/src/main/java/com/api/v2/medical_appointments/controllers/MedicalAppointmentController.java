package com.api.v2.medical_appointments.controllers;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentBookingDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentBookingService;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentCancellationService;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentCompletionService;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentRetrievalService;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
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

    @Operation(summary = "Book a public insurance medical appointment")
    @PostMapping("/public-insurance")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> publicInsuranceBook(@Valid @RequestBody MedicalAppointmentBookingDto bookingDto) {
        return bookingService.publicInsurance(bookingDto);
    }

    @Operation(summary = "Book a private insurance medical appointment")
    @PostMapping("/private-insurance")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> privateInsuranceBook(@Valid @RequestBody MedicalAppointmentBookingDto bookingDto) {
        return bookingService.privateInsurance(bookingDto);
    }

    @Operation(summary = "Book a paid by patient insurance medical appointment")
    @PostMapping("/paid-by-patient")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> paidByPatientBook(@Valid @RequestBody MedicalAppointmentBookingDto bookingDto) {
        return bookingService.paidByPatient(bookingDto);
    }

    @Operation(summary = "Retrieve a medical appointment by its id")
    @GetMapping("/{id}")
    public Mono<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findById(@PathVariable String id) {
        return retrievalService.findById(id);
    }

    @Operation(summary = "Retrieve all medical appointments")
    @GetMapping
    public Flux<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findAll() {
        return retrievalService.findAll();
    }

    @Operation(summary = "Retrieve a public insurance medical appointments by customer")
    @GetMapping("/public-insurance/{customerId}")
    public Flux<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findAllPublicInsuranceByCustomer(@PathVariable String customerId) {
        return retrievalService.findAllPublicInsuranceByCustomer(customerId);
    }

    @Operation(summary = "Retrieve a private insurance medical appointments by customer")
    @GetMapping("/private-insurance/{customerId}")
    public Flux<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findAllPrivateInsuranceByCustomer(@PathVariable String customerId) {
        return retrievalService.findAllPrivateInsuranceByCustomer(customerId);
    }

    @Operation(summary = "Retrieve a paid by patient insurance medical appointments by customer")
    @GetMapping("/paid-by-patient/{customerId}")
    public Flux<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findAllPaidByPatientByCustomer(@PathVariable String customerId) {
        return retrievalService.findAllPaidByCustomer(customerId);
    }

    @Operation(summary = "Complete a medical appointment")
    @PostMapping("medical-license-number/{medicalLicenseNumber}/appointment-id/{appointmentId}/completion")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> complete(@PathVariable String medicalLicenseNumber, @PathVariable String appointmentId) {
        return completionService.complete(medicalLicenseNumber, appointmentId);
    }

    @Operation(summary = "Cancel a medical appointment")
    @PostMapping("customer-id/{customerId}/appointment-id/{appointmentId}/cancellation")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> cancel(@PathVariable String customerId, @PathVariable String appointmentId) {
        return cancellationService.cancel(customerId, appointmentId);
    }
}
