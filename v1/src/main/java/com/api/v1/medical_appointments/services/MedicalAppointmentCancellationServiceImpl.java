package com.api.v1.medical_appointments.services;

import com.api.v1.common.EmptyResponse;
import com.api.v1.customers.controllers.CustomerControllerImpl;
import com.api.v1.customers.domain.exposed.Customer;
import com.api.v1.customers.utils.CustomerFinder;
import com.api.v1.medical_appointments.controllers.MedicalAppointmentControllerImpl;
import com.api.v1.medical_appointments.domain.MedicalAppointmentAuditTrail;
import com.api.v1.medical_appointments.domain.MedicalAppointmentAuditTrailRepository;
import com.api.v1.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import com.api.v1.medical_appointments.exceptions.CanceledMedicalAppointmentException;
import com.api.v1.medical_appointments.exceptions.CompletedMedicalAppointmentException;
import com.api.v1.medical_appointments.exceptions.InaccessibleMedicalAppointment;
import com.api.v1.medical_appointments.utils.MedicalAppointmentFinder;
import com.api.v1.medical_slots.services.exposed.MedicalSlotUpdatingService;
import com.api.v1.medical_slots.utils.MedicalSlotFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class MedicalAppointmentCancellationServiceImpl implements MedicalAppointmentCancellationService {

    private final MedicalAppointmentRepository appointmentRepository;
    private final MedicalAppointmentAuditTrailRepository auditTrailRepository;
    private final CustomerFinder customerFinder;
    private final MedicalAppointmentFinder appointmentFinder;
    private final MedicalSlotFinder medicalSlotFinder;
    private final MedicalSlotUpdatingService medicalSlotUpdatingService;

    @Override
    public Mono<ResponseEntity<EmptyResponse>> cancel(String customerId, String appointmentId) {
        return Mono.zip(
                customerFinder.findById(customerId),
                appointmentFinder.findById(appointmentId)
        ).flatMap(tuple -> {
            Customer customer = tuple.getT1();
            MedicalAppointment medicalAppointment = tuple.getT2();
            return validate(customer, medicalAppointment)
                    .then(Mono.defer(() -> {
                        MedicalAppointmentAuditTrail auditTrail = MedicalAppointmentAuditTrail.of(medicalAppointment);
                        return auditTrailRepository
                                .save(auditTrail)
                                .flatMap(_ -> {
                                   medicalAppointment.markAsCanceled();
                                   return appointmentRepository
                                           .save(medicalAppointment)
                                           .flatMap(_ -> {
                                               return medicalSlotFinder
                                                       .findActiveOrCompletedByMedicalAppointment(appointmentId)
                                                       .flatMap(foundSlot -> {
                                                           foundSlot.setMedicalAppointment(null);
                                                           return medicalSlotUpdatingService.update(foundSlot);
                                                       });
                                           });
                                });
                    }));
        }).then(Mono.defer(() -> {
            return Mono.zip(
                    linkTo(methodOn(CustomerControllerImpl.class).findById(customerId))
                            .withRel("find customer")
                            .toMono(),
                    linkTo(methodOn(MedicalAppointmentControllerImpl.class).findById(customerId, appointmentId))
                            .withRel("find medical appointment")
                            .toMono()
            ).map(tuple -> {
                return EmptyResponse
                        .empty()
                        .add(
                            tuple.getT1(),
                            tuple.getT2()
                        );
            }).map(ResponseEntity::ok);
        }));
    }

    private Mono<Object> validate(Customer customer, MedicalAppointment medicalAppointment) {
        if (!customer.getId().equals(medicalAppointment.getCustomer().getId())) {
            return Mono.error(new InaccessibleMedicalAppointment());
        }
        else if (medicalAppointment.getCompletedAt() != null && medicalAppointment.getCanceledAt() == null) {
            return Mono.error(new CompletedMedicalAppointmentException(medicalAppointment.getId()));
        }
        else if (medicalAppointment.getCompletedAt() == null && medicalAppointment.getCanceledAt() != null) {
            return Mono.error(new CanceledMedicalAppointmentException(medicalAppointment.getId()));
        }
        return Mono.empty();
    }
}
