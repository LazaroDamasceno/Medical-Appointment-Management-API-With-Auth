package com.api.v1.medical_appointments.services;

import com.api.v1.customers.controllers.CustomerController;
import com.api.v1.customers.domain.exposed.Customer;
import com.api.v1.customers.utils.CustomerFinder;
import com.api.v1.medical_appointments.MedicalAppointmentController;
import com.api.v1.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import com.api.v1.medical_appointments.exceptions.InaccessibleMedicalAppointment;
import com.api.v1.medical_appointments.responses.MedicalAppointmentResponseDto;
import com.api.v1.medical_appointments.utils.MedicalAppointmentFinder;
import com.api.v1.medical_slots.controllers.MedicalSlotController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class MedicalAppointmentRetrievalServiceImpl implements MedicalAppointmentRetrievalService {

    private final CustomerFinder customerFinder;
    private final MedicalAppointmentFinder medicalAppointmentFinder;
    private final MedicalAppointmentRepository medicalAppointmentRepository;

    @Override
    public Mono<ResponseEntity<MedicalAppointmentResponseDto>> findById(String customerId, String appointmentId) {
        return customerFinder.findById(customerId)
                .zipWith(medicalAppointmentFinder.findById(appointmentId))
                .flatMap(tuple -> {
                    Customer customer = tuple.getT1();
                    MedicalAppointment medicalAppointment = tuple.getT2();
                    return onNonAssociatedCustomer(customer, medicalAppointment)
                            .then(Mono.defer(() -> medicalAppointmentRepository
                                    .findById(customer, medicalAppointment.getId())
                                    .map(MedicalAppointment::toDto)
                                    .flatMap(response -> Mono.zip(                                            linkTo(methodOn(CustomerController.class)
                                                    .findById(customerId))
                                                    .withRel("find customer")
                                                    .toMono(),
                                            linkTo(methodOn(MedicalAppointmentController.class)
                                                    .findAllByCustomer(customerId))
                                                    .withRel("find all by customer")
                                                    .toMono(),
                                            response::add
                                    ))
                            ));
                })
                .map(ResponseEntity::ok);
    }

    @Override
    public ResponseEntity<Flux<MedicalAppointmentResponseDto>> findAllByCustomer(String customerId) {
        var flux = customerFinder
                .findById(customerId)
                .flatMapMany(foundCustomer -> {
                    return  medicalAppointmentRepository.findAllByCustomer(foundCustomer)
                            .map(MedicalAppointment::toDto);
                });
        return ResponseEntity.ok(flux);
    }

    @Override
    public ResponseEntity<Flux<MedicalAppointmentResponseDto>> findAll() {
        var flux = medicalAppointmentRepository
                .findAll()
                .map(MedicalAppointment::toDto);
        return ResponseEntity.ok(flux);
    }

    private Mono<Object> onNonAssociatedCustomer(Customer customer, MedicalAppointment medicalAppointment) {
        if (!customer.getId().equals(medicalAppointment.getDoctor().getId())) {
            return Mono.error(new InaccessibleMedicalAppointment());
        }
        return Mono.empty();
    }
}
