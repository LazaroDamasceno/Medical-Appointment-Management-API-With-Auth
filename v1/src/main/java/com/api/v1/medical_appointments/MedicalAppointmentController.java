package com.api.v1.medical_appointments;

import com.api.v1.medical_appointments.responses.MedicalAppointmentResponseDto;
import com.api.v1.medical_appointments.services.MedicalAppointmentRetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/medical-appointments")
public class MedicalAppointmentController {

    private final MedicalAppointmentRetrievalService retrievalService;

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

    @GetMapping
    public ResponseEntity<Flux<MedicalAppointmentResponseDto>> findAll() {
        return retrievalService.findAll();
    }
}
