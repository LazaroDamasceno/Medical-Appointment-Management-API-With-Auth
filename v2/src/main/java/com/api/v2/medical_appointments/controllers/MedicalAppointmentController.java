package com.api.v2.medical_appointments.controllers;
import com.api.v2.common.MLN;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentBookingDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentBookingService;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentCancellationService;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentCompletionService;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentRetrievalService;
import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
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

    @PostMapping("/public-insurance")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> publicInsuranceBook(@Valid @RequestBody MedicalAppointmentBookingDto bookingDto) {
        return bookingService.publicInsuranceBook(bookingDto);
    }

    @PostMapping("/private-insurance")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> privateInsuranceBook(@Valid @RequestBody MedicalAppointmentBookingDto bookingDto) {
        return bookingService.privateInsuranceBook(bookingDto);
    }

    @PostMapping("/paid-by-patient")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> paidByPatientBook(@Valid @RequestBody MedicalAppointmentBookingDto bookingDto) {
        return bookingService.paidByPatientBook(bookingDto);
    }

    @GetMapping("/{id}")
    public Mono<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findById(@PathVariable String id) {
        return retrievalService.findById(id);
    }

    @GetMapping
    public Flux<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findAll() {
        return retrievalService.findAll();
    }

    @GetMapping("/public-insurance/{customerId}")
    public Flux<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findAllPublicInsuranceByCustomer(@PathVariable String customerId) {
        return retrievalService.findAllPublicInsuranceByCustomer(customerId);
    }

    @GetMapping("/private-insurance/{customerId}")
    public Flux<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findAllPrivateInsuranceByCustomer(@PathVariable String customerId) {
        return retrievalService.findAllPrivateInsuranceByCustomer(customerId);
    }

    @GetMapping("/paid-by-patient/{customerId}")
    public Flux<HalResourceWrapper<MedicalAppointmentResponseDto, Void>> findAllPaidByPatientByCustomer(@PathVariable String customerId) {
        return retrievalService.findAllPaidByCustomer(customerId);
    }

    @PatchMapping("medical-license-number/{medicalLicenseNumber}/appointment-id/{appointmentId}/completion")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> complete(@PathVariable @MLN String medicalLicenseNumber, @PathVariable String appointmentId) {
        return completionService.complete(medicalLicenseNumber, appointmentId);
    }

    @PatchMapping("customer-id/{customerId}/appointment-id/{appointmentId}/cancellation")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> cancel(@PathVariable String customerId, @PathVariable String appointmentId) {
        return cancellationService.cancel(customerId, appointmentId);
    }
}
