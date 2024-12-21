package com.api.v2.services.impl;

import com.api.v2.domain.Doctor;
import com.api.v2.domain.DoctorRepository;
import com.api.v2.dtos.DoctorHiringDto;
import com.api.v2.dtos.DoctorResponseDto;
import com.api.v2.services.DoctorHiringService;
import com.api.v2.services.PersonRegistrationService;
import com.api.v2.utils.DoctorResponseMapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DoctorHiringServiceImpl implements DoctorHiringService {

    private final PersonRegistrationService personRegistrationService;
    private final DoctorRepository doctorRepository;

    public DoctorHiringServiceImpl(
            PersonRegistrationService personRegistrationService,
            DoctorRepository doctorRepository
    ) {
        this.personRegistrationService = personRegistrationService;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Mono<DoctorResponseDto> hire(@Valid DoctorHiringDto hiringDto) {
        return personRegistrationService
                .register(hiringDto.personRegistrationDto())
                .flatMap(person -> {
                    return Mono.defer(() -> {
                        Doctor doctor = Doctor.create(hiringDto.medicalLicenseNumber(), person);
                        return doctorRepository.save(doctor);
                    });
                })
                .flatMap(DoctorResponseMapper::mapToMono);
    }
}
