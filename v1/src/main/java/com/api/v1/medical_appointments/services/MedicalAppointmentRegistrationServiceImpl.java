package com.api.v1.medical_appointments.services;

import com.api.v1.common.DuplicatedBookingDateTimeException;
import com.api.v1.customers.domain.exposed.Customer;
import com.api.v1.customers.utils.CustomerFinder;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.utils.DoctorFinder;
import com.api.v1.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import com.api.v1.medical_appointments.responses.MedicalAppointmentResponseDto;
import com.api.v1.medical_slots.domain.MedicalSlot;
import com.api.v1.medical_slots.exceptions.InaccessibleMedicalSlot;
import com.api.v1.medical_slots.services.exposed.MedicalSlotUpdatingService;
import com.api.v1.medical_slots.utils.MedicalSlotFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;

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
                                                                        LocalDateTime bookedAt
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
                                                        .map(_ -> {
                                                            MedicalAppointmentResponseDto responseDto = appointment.toDto();
                                                            return ResponseEntity
                                                                    .created(URI.create("/api/v1/medical-appointment"))
                                                                    .body(responseDto);
                                                        });
                                            });
                                }));
                    });
        });
    }

    private Mono<Object> onDuplicatedBookingDateTime(Customer customer, LocalDateTime bookedAt) {
        return medicalAppointmentRepository
                .findByCustomerAndBookedAt(customer, bookedAt)
                .switchIfEmpty(Mono.empty())
                .flatMap(_ -> {
                    String message = "Provided booking date and time is currently in use in another active medical appointment.";
                    return Mono.error(new DuplicatedBookingDateTimeException(message));
                });
    }

    private Mono<Object> onNonAssociatedDoctorWithMedicalSlot(Doctor doctor, MedicalSlot medicalSlot) {
        if (doctor.getId().equals(medicalSlot.getDoctor().getId())) {
            return Mono.error(new InaccessibleMedicalSlot());
        }
        return Mono.empty();
    }
}
