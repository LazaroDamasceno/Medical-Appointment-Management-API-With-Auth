package com.api.v2.medical_appointments.services.impl;

import com.api.v2.customers.utils.CustomerFinderUtil;
import com.api.v2.doctors.utils.DoctorFinderUtil;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentBookingDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.exceptions.UnavailableGivenBookingDateTimeException;
import com.api.v2.medical_appointments.services.interfaces.MedicalAppointmentBookingService;
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class MedicalAppointmentBookingServiceImpl implements MedicalAppointmentBookingService {

    private final MedicalAppointmentRepository medicalAppointmentRepository;
    private final MedicalSlotRepository medicalSlotRepository;
    private final DoctorFinderUtil doctorFinderUtil;
    private final CustomerFinderUtil customerFinderUtil;

    public MedicalAppointmentBookingServiceImpl(
            MedicalAppointmentRepository medicalAppointmentRepository,
            MedicalSlotRepository medicalSlotRepository,
            DoctorFinderUtil doctorFinderUtil,
            CustomerFinderUtil customerFinderUtil
    ) {
        this.medicalAppointmentRepository = medicalAppointmentRepository;
        this.medicalSlotRepository = medicalSlotRepository;
        this.doctorFinderUtil = doctorFinderUtil;
        this.customerFinderUtil = customerFinderUtil;
    }

    @Override
    public Mono<MedicalAppointmentResponseDto> book(@Valid MedicalAppointmentBookingDto bookingDto) {
        var monoDoctor = doctorFinderUtil.findByLicenseNumber(bookingDto.medicalLicenseNumber());
        var monoCustomer = customerFinderUtil.findBySsn(bookingDto.ssn());
        return Mono.zip(monoDoctor, monoCustomer)
                .flatMap(tuple -> {
                    return medicalSlotRepository
                            .findActiveByDoctorAndAvailableAt(bookingDto.bookedAt(), tuple.getT1())
                            .singleOptional()
                            .flatMap(optional -> {
                                MedicalSlot medicalSlot = optional.get();
                                return onUnavailableGivenBookingDateTime(medicalSlot)
                                        .then(onAppointmentExistsForSlot(medicalSlot.getMedicalAppointment()))
                                        .then(Mono.defer(() -> {
                                            MedicalAppointment medicalAppointment = MedicalAppointment.create(
                                                    tuple.getT2(),
                                                    tuple.getT1(),
                                                    bookingDto.bookedAt()
                                            );
                                            medicalSlot.setMedicalAppointment(medicalAppointment);
                                            return medicalSlotRepository
                                                    .save(medicalSlot)
                                                    .then(Mono.defer(() -> {
                                                        return medicalAppointmentRepository.save(medicalAppointment);
                                                    }));
                                        }));
                            })
                            .flatMap(MedicalAppointmentResponseMapper::mapToMono);
                });
    }

    private Mono<Void> onUnavailableGivenBookingDateTime(MedicalSlot medicalSlot) {
        return Mono.just(medicalSlot)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(UnavailableGivenBookingDateTimeException::new))
                .then(Mono.empty());
    }

    public Mono<Void> onAppointmentExistsForSlot(MedicalAppointment medicalAppointment) {
        return Mono.just(medicalAppointment)
                .filter(Objects::nonNull)
                .then(Mono.error(UnavailableGivenBookingDateTimeException::new))
                .switchIfEmpty(Mono.empty())
                .then();
    }
}
