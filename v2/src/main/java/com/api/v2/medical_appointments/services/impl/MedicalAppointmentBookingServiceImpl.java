package com.api.v2.medical_appointments.services.impl;

import com.api.v2.customers.domain.Customer;
import com.api.v2.customers.utils.CustomerFinderUtil;
import com.api.v2.doctors.domain.Doctor;
import com.api.v2.doctors.utils.DoctorFinderUtil;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentBookingDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.exceptions.UnavailableBookingDateTimeException;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentBookingService;
import com.api.v2.medical_appointments.utils.MedicalAppointmentFinderUtil;
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.exceptions.UnavailableMedicalSlotException;
import com.api.v2.medical_slots.utils.MedicalSlotFinderUtil;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class MedicalAppointmentBookingServiceImpl implements MedicalAppointmentBookingService {

    private final MedicalSlotFinderUtil medicalSlotFinderUtil;
    private final MedicalAppointmentFinderUtil medicalAppointmentFinderUtil;
    private final MedicalSlotRepository medicalSlotRepository;
    private final MedicalAppointmentRepository medicalAppointmentRepository;
    private final DoctorFinderUtil doctorFinderUtil;
    private final CustomerFinderUtil customerFinderUtil;

    public MedicalAppointmentBookingServiceImpl(
            MedicalSlotFinderUtil medicalSlotFinderUtil,
            MedicalAppointmentFinderUtil medicalAppointmentFinderUtil,
            MedicalSlotRepository medicalSlotRepository,
            MedicalAppointmentRepository medicalAppointmentRepository,
            DoctorFinderUtil doctorFinderUtil,
            CustomerFinderUtil customerFinderUtil
    ) {
        this.medicalSlotFinderUtil = medicalSlotFinderUtil;
        this.medicalAppointmentFinderUtil = medicalAppointmentFinderUtil;
        this.medicalSlotRepository = medicalSlotRepository;
        this.medicalAppointmentRepository = medicalAppointmentRepository;
        this.doctorFinderUtil = doctorFinderUtil;
        this.customerFinderUtil = customerFinderUtil;
    }

    @Override
    public Mono<MedicalAppointmentResponseDto> publicInsuranceBook(@Valid MedicalAppointmentBookingDto bookingDto) {
        Mono<Doctor> doctorMono = doctorFinderUtil.findByLicenseNumber(bookingDto.medicalLicenseNumber());
        Mono<Customer> customerMono = customerFinderUtil.findBySsn(bookingDto.ssn());
        return doctorMono
                .zipWith(customerMono)
                .flatMap(tuple -> {
                    Doctor doctor = tuple.getT1();
                    Customer customer = tuple.getT2();
                    return onFoundMedicalSlot(doctor, bookingDto.bookedAt())
                            .flatMap(slot -> {
                                return onUnavailableBookingDateTime(customer, doctor, bookingDto.bookedAt())
                                        .then(Mono.defer(() -> {
                                            MedicalAppointment medicalAppointment = MedicalAppointment.create("public insurance", customer, doctor, bookingDto.bookedAt());
                                            slot.setMedicalAppointment(medicalAppointment);
                                            return medicalSlotRepository
                                                    .save(slot)
                                                    .then(medicalAppointmentRepository
                                                            .save(medicalAppointment)
                                                            .flatMap(MedicalAppointmentResponseMapper::mapToMono)
                                                    );
                                        }));
                            });

                });
    }

    @Override
    public Mono<MedicalAppointmentResponseDto> privateInsuranceBook(@Valid MedicalAppointmentBookingDto bookingDto) {
        Mono<Doctor> doctorMono = doctorFinderUtil.findByLicenseNumber(bookingDto.medicalLicenseNumber());
        Mono<Customer> customerMono = customerFinderUtil.findBySsn(bookingDto.ssn());
        return doctorMono
                .zipWith(customerMono)
                .flatMap(tuple -> {
                    Doctor doctor = tuple.getT1();
                    Customer customer = tuple.getT2();
                    return onFoundMedicalSlot(doctor, bookingDto.bookedAt())
                            .flatMap(slot -> {
                                return onUnavailableBookingDateTime(customer, doctor, bookingDto.bookedAt())
                                        .then(Mono.defer(() -> {
                                            MedicalAppointment medicalAppointment = MedicalAppointment.create("private insurance", customer, doctor, bookingDto.bookedAt());
                                            slot.setMedicalAppointment(medicalAppointment);
                                            return medicalSlotRepository
                                                    .save(slot)
                                                    .then(medicalAppointmentRepository
                                                            .save(medicalAppointment)
                                                            .flatMap(MedicalAppointmentResponseMapper::mapToMono)
                                                    );
                                        }));
                            });

                });
    }

    @Override
    public Mono<MedicalAppointmentResponseDto> paidByPatientBook(@Valid MedicalAppointmentBookingDto bookingDto) {
        Mono<Doctor> doctorMono = doctorFinderUtil.findByLicenseNumber(bookingDto.medicalLicenseNumber());
        Mono<Customer> customerMono = customerFinderUtil.findBySsn(bookingDto.ssn());
        return doctorMono
                .zipWith(customerMono)
                .flatMap(tuple -> {
                    Doctor doctor = tuple.getT1();
                    Customer customer = tuple.getT2();
                    return onFoundMedicalSlot(doctor, bookingDto.bookedAt())
                            .flatMap(slot -> {
                                return onUnavailableBookingDateTime(customer, doctor, bookingDto.bookedAt())
                                        .then(Mono.defer(() -> {
                                            MedicalAppointment medicalAppointment = MedicalAppointment.create("paid by patient", customer, doctor, bookingDto.bookedAt());
                                            slot.setMedicalAppointment(medicalAppointment);
                                            return medicalSlotRepository
                                                    .save(slot)
                                                    .then(medicalAppointmentRepository
                                                            .save(medicalAppointment)
                                                            .flatMap(MedicalAppointmentResponseMapper::mapToMono)
                                                    );
                                        }));
                            });

                });
    }

    private Mono<MedicalSlot> onFoundMedicalSlot(Doctor doctor, LocalDateTime availableAt) {
        String message = "There's no medical slot registered for the datetime %s.".formatted(availableAt);
        return medicalSlotFinderUtil
                .findActiveByDoctorAndAvailableAt(doctor, availableAt)
                .switchIfEmpty(Mono.error(new UnavailableMedicalSlotException(message)));
    }

    private Mono<Void> onUnavailableBookingDateTime(Customer customer, Doctor doctor, LocalDateTime bookedAt) {
        return medicalAppointmentFinderUtil
                .findActiveByCustomerAndDoctorAndBookedAt(customer, doctor, bookedAt)
                .hasElement()
                .flatMap(exists -> {
                   if (exists) {
                       return Mono.error(new UnavailableBookingDateTimeException(bookedAt));
                   }
                   return Mono.empty();
                });
    }
}
