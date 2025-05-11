package com.api.v1.medical_appointments.services;

import com.api.v1.common.DuplicatedBookingDateTimeException;
import com.api.v1.common.NonExistentBookingDateTimeException;
import com.api.v1.common.ProfessionalStatus;
import com.api.v1.customers.domain.exposed.Customer;
import com.api.v1.customers.utils.CustomerFinder;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.exceptions.TerminatedDoctorException;
import com.api.v1.doctors.utils.DoctorFinder;
import com.api.v1.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import com.api.v1.medical_appointments.enums.MedicalAppointmentStatus;
import com.api.v1.medical_appointments.exceptions.SelfBookingAppointmentException;
import com.api.v1.medical_appointments.responses.MedicalAppointmentResponseDto;
import com.api.v1.medical_slots.domain.exposed.MedicalSlot;
import com.api.v1.medical_slots.exceptions.InaccessibleMedicalSlot;
import com.api.v1.medical_slots.services.exposed.MedicalSlotUpdatingService;
import com.api.v1.medical_slots.utils.MedicalSlotFinder;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;

@Service
public class MedicalAppointmentRegistrationServiceImpl implements MedicalAppointmentRegistrationService {

    private final MedicalAppointmentRepository medicalAppointmentRepository;
    private final CustomerFinder customerFinder;
    private final DoctorFinder doctorFinder;
    private final MedicalSlotFinder medicalSlotFinder;
    private final MedicalSlotUpdatingService medicalSlotUpdatingService;

    public MedicalAppointmentRegistrationServiceImpl(MedicalAppointmentRepository medicalAppointmentRepository,
                                                     CustomerFinder customerFinder,
                                                     DoctorFinder doctorFinder,
                                                     MedicalSlotFinder medicalSlotFinder,
                                                     MedicalSlotUpdatingService medicalSlotUpdatingService
    ) {
        this.medicalAppointmentRepository = medicalAppointmentRepository;
        this.customerFinder = customerFinder;
        this.doctorFinder = doctorFinder;
        this.medicalSlotFinder = medicalSlotFinder;
        this.medicalSlotUpdatingService = medicalSlotUpdatingService;
    }

    @Override
    public Mono<ResponseEntity<MedicalAppointmentResponseDto>> register(String customerId,
                                                                        String doctorLicenseNumber,
                                                                        @NotNull LocalDateTime bookedAt
    ) {
        return Mono.zip(
                customerFinder.findById(customerId),
                doctorFinder.findByLicenseNumber(doctorLicenseNumber)
        ).flatMap(tuple -> {
            Customer customer = tuple.getT1();
            Doctor doctor = tuple.getT2();
            return medicalSlotFinder
                    .findActiveByDoctorAndAvailableAt(doctor.getId(), bookedAt)
                    .flatMap(foundSlot -> {
                        return validate(customer, doctor, bookedAt, foundSlot)
                                .then(Mono.defer(() -> {
                                    MedicalAppointment medicalAppointment = MedicalAppointment.of(customer, doctor, bookedAt);
                                    return medicalAppointmentRepository
                                            .save(medicalAppointment)
                                            .flatMap(appointment -> {
                                                return medicalSlotUpdatingService
                                                        .update(foundSlot, appointment)
                                                        .map((_ -> {
                                                            MedicalAppointmentResponseDto responseDto = appointment.toDto();
                                                            return ResponseEntity
                                                                    .created(URI.create("/api/v1/medical-appointments"))
                                                                    .body(responseDto);
                                                        }));
                                            });
                                }));
                    });
        });
    }

    private Mono<Object> validate(Customer customer,
                                  Doctor doctor,
                                  LocalDateTime bookedAt,
                                  MedicalSlot medicalSlot
    ) {
        return medicalAppointmentRepository
                .findActiveByCustomerAndBookedAt(customer.getId(), bookedAt)
                .singleOptional()
                .flatMap(optional -> {
                    if (optional.isEmpty()) {
                        Mono.error(new NullPointerException());
                    }
                    else if (optional.get().getStatus().equals(MedicalAppointmentStatus.ACTIVE)) {
                        String message = "Provided booking date and time is currently in use in another active medical appointment.";
                        return Mono.error(new DuplicatedBookingDateTimeException(message));
                    }
                    else if (customer.getPerson().getId().equals(medicalSlot.getDoctor().getPerson().getId())) {
                        return Mono.error(new SelfBookingAppointmentException(doctor.getLicenseNumber()));
                    }
                    else if (!doctor.getId().equals(medicalSlot.getDoctor().getId())) {
                        return Mono.error(new InaccessibleMedicalSlot(doctor.getLicenseNumber()));
                    }
                    else if (doctor.getStatus().equals(ProfessionalStatus.TERMINATED)) {
                        return Mono.error(new TerminatedDoctorException(doctor.getLicenseNumber()));
                    }
                    return Mono.empty();
                })
                .then(medicalSlotFinder
                        .findActiveByDoctorAndAvailableAt(doctor.getId(), bookedAt)
                        .switchIfEmpty(Mono.error(new NonExistentBookingDateTimeException()))
                        .flatMap(_ -> Mono.empty())
                );
    }
}
