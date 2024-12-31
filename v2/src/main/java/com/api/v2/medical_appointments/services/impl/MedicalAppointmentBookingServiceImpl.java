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
    private final MedicalAppointmentFinderUtil medicalAppointmentFinderUtil;

    public MedicalAppointmentBookingServiceImpl(
            TelegramBotMessageSenderService messageSenderService,
            MedicalSlotRepository medicalSlotRepository,
            MedicalAppointmentRepository medicalAppointmentRepository,
            DoctorFinderUtil doctorFinderUtil,
            CustomerFinderUtil customerFinderUtil,
            MedicalSlotFinderUtil medicalSlotFinderUtil,
            MedicalAppointmentFinderUtil medicalAppointmentFinderUtil
    ) {
        this.messageSenderService = messageSenderService;
        this.medicalSlotRepository = medicalSlotRepository;
        this.medicalAppointmentRepository = medicalAppointmentRepository;
        this.doctorFinderUtil = doctorFinderUtil;
        this.customerFinderUtil = customerFinderUtil;
        this.medicalSlotFinderUtil = medicalSlotFinderUtil;
        this.medicalAppointmentFinderUtil = medicalAppointmentFinderUtil;
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
                    return null;
                });
    }


    private Mono<MedicalSlot> onFoundMedicalSlot(Doctor doctor, LocalDateTime availableAt) {
        Mono<MedicalSlot> medicalSlotMono = medicalSlotFinderUtil.findActiveByDoctorAndAvailableAt(doctor, availableAt);
        return medicalSlotMono
                .hasElement()
                .flatMap(exists -> {
                   if (exists) {
                       return medicalSlotMono.single();
                   }
                   String message = "There's no medical slot which available datetime is equals to given datetime.";
                   return Mono.error(new UnavailableMedicalSlotException(message));
                });
    }

    private Mono<Void> onUnavailableBookingDateTime(Doctor doctor, Customer customer, LocalDateTime availableAt) {
        return medicalAppointmentFinderUtil
                .findActiveByCustomerAndDoctorAndBookedAt(customer, doctor, availableAt)
                .hasElement()
                .flatMap(exists -> {
                   if (exists) {
                       return Mono.error(UnavailableBookingDateTimeException::new);
                   }
                   return Mono.empty();
                });
    }
}
