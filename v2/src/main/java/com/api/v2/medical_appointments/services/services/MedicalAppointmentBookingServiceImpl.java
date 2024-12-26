package com.api.v2.medical_appointments.services.services;

import com.api.v2.customers.domain.Customer;
import com.api.v2.customers.utils.CustomerFinderUtil;
import com.api.v2.doctors.domain.Doctor;
import com.api.v2.doctors.utils.DoctorFinderUtil;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentBookingDto;
import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.exceptions.DuplicatedGivenBookingDateTimeException;
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MedicalAppointmentBookingServiceImpl implements MedicalAppointmentBookingService {

    private final MedicalAppointmentRepository medicalAppointmentRepository;
    private final DoctorFinderUtil doctorFinderUtil;
    private final CustomerFinderUtil customerFinderUtil;
    private final MedicalSlotRepository medicalSlotRepository;

    public MedicalAppointmentBookingServiceImpl(
            MedicalAppointmentRepository medicalAppointmentRepository,
            DoctorFinderUtil doctorFinderUtil,
            CustomerFinderUtil customerFinderUtil,
            MedicalSlotRepository medicalSlotRepository
    ) {
        this.medicalAppointmentRepository = medicalAppointmentRepository;
        this.doctorFinderUtil = doctorFinderUtil;
        this.customerFinderUtil = customerFinderUtil;
        this.medicalSlotRepository = medicalSlotRepository;
    }

    @Override
    public Mono<MedicalAppointmentResponseDto> book(@Valid MedicalAppointmentBookingDto bookingDto) {
        Mono<Doctor> doctorMono = doctorFinderUtil.findByLicenseNumber(bookingDto.medicalLicenseNumber());
        Mono<Customer> customerMono = customerFinderUtil.findBySsn(bookingDto.ssn());
        return doctorMono
                .zipWith(customerMono)
                .flatMap(tuple -> {
                    return medicalSlotRepository
                            .findByDoctorAndAvailableAt(bookingDto.bookedAt(), tuple.getT1())
                            .singleOptional()
                            .flatMap(optional -> {
                               if (optional.isPresent()) {
                                   return Mono.error(new DuplicatedGivenBookingDateTimeException());
                               }
                               return Mono.defer(() -> {
                                   MedicalAppointment medicalAppointment = MedicalAppointment.create(
                                           tuple.getT2(),
                                           tuple.getT1(),
                                           bookingDto.bookedAt()
                                   );
                                   return medicalAppointmentRepository.save(medicalAppointment);
                               });
                            })
                            .flatMap(MedicalAppointmentResponseMapper::mapToMono);
                });
    }
}
