package com.api.v1.medical_appointments.services;

import com.api.v1.common.DuplicatedBookingDateTimeException;
import com.api.v1.customers.controllers.CustomerController;
import com.api.v1.customers.domain.exposed.Customer;
import com.api.v1.customers.utils.CustomerFinder;
import com.api.v1.doctors.controllers.DoctorController;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.utils.DoctorFinder;
import com.api.v1.medical_appointments.controllers.MedicalAppointmentController;
import com.api.v1.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import com.api.v1.medical_appointments.enums.MedicalAppointmentStatus;
import com.api.v1.medical_appointments.responses.MedicalAppointmentResponseDto;
import com.api.v1.medical_slots.domain.MedicalSlot;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;
import com.api.v1.medical_slots.exceptions.InaccessibleMedicalSlot;
import com.api.v1.medical_slots.exceptions.NotActiveMedicalSlotException;
import com.api.v1.medical_slots.services.exposed.MedicalSlotUpdatingService;
import com.api.v1.medical_slots.utils.MedicalSlotFinder;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class MedicalAppointmentRegistrationServiceImpl implements MedicalAppointmentRegistrationService {

    private final MedicalAppointmentRepository medicalAppointmentRepository;
    private final CustomerFinder customerFinder;
    private final DoctorFinder doctorFinder;
    private final MedicalSlotFinder medicalSlotFinder;
    private final MedicalSlotUpdatingService medicalSlotUpdatingService;

    @Override
    public Mono<ResponseEntity<MedicalAppointmentResponseDto>> register(String customerId,
                                                                        String doctorId,
                                                                        @NotNull LocalDateTime bookedAt
    ) {
        return Mono.zip(
                customerFinder.findById(customerId),
                doctorFinder.findById(doctorId)
        ).flatMap(tuple -> {
            Customer customer = tuple.getT1();
            Doctor doctor = tuple.getT2();
            return medicalSlotFinder
                    .findActiveByDoctorAndAvailableAt(doctor, bookedAt)
                    .flatMap(foundSlot -> {
                        return onDuplicatedBookingDateTime(customer, bookedAt)
                                .then(onNonAssociatedDoctorWithMedicalSlot(doctor, foundSlot))
                                .then(Mono.defer(() -> {
                                    MedicalAppointment medicalAppointment = MedicalAppointment.of(customer, doctor, bookedAt);
                                    return medicalAppointmentRepository
                                            .save(medicalAppointment)
                                            .flatMap(appointment -> {
                                                return medicalSlotUpdatingService
                                                        .update(foundSlot, appointment)
                                                        .flatMap(_ -> Mono.zip(
                                                                        linkTo(methodOn(CustomerController.class).findById(customerId))
                                                                                .withRel("find customer")
                                                                                .toMono(),
                                                                        linkTo(methodOn(DoctorController.class).findById(doctorId))
                                                                                .withRel("find doctor")
                                                                                .toMono(),
                                                                        linkTo(methodOn(MedicalAppointmentController.class).findAllByCustomer(customerId))
                                                                                .withRel("find all by customer")
                                                                                .toMono(),
                                                                        linkTo(methodOn(MedicalAppointmentController.class).findAllByDoctor(doctorId))
                                                                                .withRel("find all by doctor")
                                                                                .toMono()
                                                                ).map(links -> appointment
                                                                        .toDto()
                                                                        .add(
                                                                                links.getT1(),
                                                                                links.getT2(),
                                                                                links.getT3(),
                                                                                links.getT4()
                                                                        )
                                                                ).map(response -> ResponseEntity
                                                                        .created(URI.create("/api/v1/medical-appointment"))
                                                                        .body(response)
                                                                )
                                                        );
                                            });
                                }));
                    });
        });
    }

    private Mono<Object> onDuplicatedBookingDateTime(Customer customer, LocalDateTime bookedAt) {
        return medicalAppointmentRepository
                .findByCustomerAndBookedAt(customer.getId(), bookedAt)
                .singleOptional()
                .flatMap(optional -> {
                    if (optional.isEmpty()) {
                        Mono.error(new NullPointerException());
                    }
                    else if (optional.get().getStatus().equals(MedicalAppointmentStatus.ACTIVE)) {
                        String message = "Provided booking date and time is currently in use in another active medical appointment.";
                        return Mono.error(new DuplicatedBookingDateTimeException(message));
                    }
                    return Mono.empty();
                });
    }

    private Mono<Object> onNonAssociatedDoctorWithMedicalSlot(Doctor doctor, MedicalSlot medicalSlot) {
        if (doctor.getId().equals(medicalSlot.getDoctor().getId())) {
            return Mono.error(new InaccessibleMedicalSlot());
        }
        return Mono.empty();
    }
}
