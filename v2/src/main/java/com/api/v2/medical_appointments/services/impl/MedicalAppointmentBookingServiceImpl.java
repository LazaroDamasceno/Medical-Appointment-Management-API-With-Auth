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
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.utils.MedicalSlotFinderUtil;
import com.api.v2.telegram_bot.services.interfaces.TelegramBotMessageSenderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class MedicalAppointmentBookingServiceImpl implements MedicalAppointmentBookingService {

    private final TelegramBotMessageSenderService messageSenderService;
    private final MedicalSlotRepository medicalSlotRepository;
    private final MedicalAppointmentRepository medicalAppointmentRepository;
    private final DoctorFinderUtil doctorFinderUtil;
    private final CustomerFinderUtil customerFinderUtil;
    private final MedicalSlotFinderUtil medicalSlotFinderUtil;

    public MedicalAppointmentBookingServiceImpl(
            TelegramBotMessageSenderService messageSenderService,
            MedicalSlotRepository medicalSlotRepository,
            MedicalAppointmentRepository medicalAppointmentRepository,
            DoctorFinderUtil doctorFinderUtil,
            CustomerFinderUtil customerFinderUtil,
            MedicalSlotFinderUtil medicalSlotFinderUtil
    ) {
        this.messageSenderService = messageSenderService;
        this.medicalSlotRepository = medicalSlotRepository;
        this.medicalAppointmentRepository = medicalAppointmentRepository;
        this.doctorFinderUtil = doctorFinderUtil;
        this.customerFinderUtil = customerFinderUtil;
        this.medicalSlotFinderUtil = medicalSlotFinderUtil;
    }

    @Override
    public Mono<MedicalAppointmentResponseDto> book(@Valid MedicalAppointmentBookingDto bookingDto) {
        Mono<Doctor> doctorMono = doctorFinderUtil.findByLicenseNumber(bookingDto.medicalLicenseNumber());
        Mono<Customer> customerMono = customerFinderUtil.findBySsn(bookingDto.ssn());
        return doctorMono
                .zipWith(customerMono)
                .flatMap(tuple -> {
                    Doctor doctor = tuple.getT1();
                    Customer customer = tuple.getT2();
                    return onUnavailableBookingDateTime(customer, doctor, bookingDto.bookedAt())
                            .then(Mono.defer(() -> {
                                return medicalSlotFinderUtil
                                        .findActiveByDoctorAndAvailableAt(doctor, bookingDto.bookedAt())
                                        .flatMap(medicalSlot -> {
                                            try {
                                                messageSenderService.sendMessage("A new medical appointment was booked.");
                                            } catch (TelegramApiException e) {
                                                throw new RuntimeException(e);
                                            }
                                            MedicalAppointment medicalAppointment = MedicalAppointment.create(customer, doctor, bookingDto.bookedAt());
                                            return medicalAppointmentRepository
                                                    .save(medicalAppointment)
                                                    .then(Mono.defer(() -> {
                                                        medicalSlot.setMedicalAppointment(medicalAppointment);
                                                        return medicalSlotRepository.save(medicalSlot);
                                                    }))
                                                    .then(Mono.just(medicalAppointment));
                                        });
                            }));
                })
                .flatMap(MedicalAppointmentResponseMapper::mapToMono);
    }

    private Mono<Void> onUnavailableBookingDateTime(Customer customer, Doctor doctor, LocalDateTime bookedAt) {
        return medicalAppointmentRepository
                .findActiveByCustomerAndDoctorAndBookedAt(customer, doctor, bookedAt)
                .singleOptional()
                .flatMap(optional -> {
                    if (optional.isPresent()) {
                        return Mono.error(UnavailableBookingDateTimeException::new);
                    }
                    return Mono.empty();
                });
    }
}
