package com.api.v2.services.impl;

import com.api.v2.domain.Doctor;
import com.api.v2.domain.DoctorRepository;
import com.api.v2.dtos.DoctorHiringDto;
import com.api.v2.dtos.DoctorResponseDto;
import com.api.v2.exceptions.DuplicatedEmailException;
import com.api.v2.exceptions.DuplicatedMedicalLicenseNumberException;
import com.api.v2.exceptions.DuplicatedSsnException;
import com.api.v2.services.DoctorHiringService;
import com.api.v2.utils.DoctorResponseMapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DoctorHiringServiceImpl implements DoctorHiringService {

    private final PersonRegistrationServiceImpl personRegistrationService;
    private final DoctorRepository doctorRepository;

    public DoctorHiringServiceImpl(
            PersonRegistrationServiceImpl personRegistrationService,
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
                    return onDuplicatedMedicalLicenseNumber(hiringDto.medicalLicenseNumber())
                            .then(onDuplicatedSsn(hiringDto.personRegistrationDto().ssn()))
                            .then(onDuplicatedEmail(hiringDto.personRegistrationDto().email()))
                            .then(Mono.defer(() -> {
                                Doctor doctor = Doctor.create(hiringDto.medicalLicenseNumber(), person);
                                return doctorRepository.save(doctor);
                            }));
                })
                .flatMap(DoctorResponseMapper::mapToMono);
    }

    private Mono<Void> onDuplicatedMedicalLicenseNumber(String medicalLicenseNumber) {
        return doctorRepository
                .findByLicenseNumber(medicalLicenseNumber)
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(DuplicatedMedicalLicenseNumberException::new);
                    }
                    return Mono.empty();
                });
    }

    private Mono<Void>  onDuplicatedSsn(String ssn) {
        return doctorRepository
                .findAll()
                .filter(c -> c.getPerson().getSsn().equals(ssn))
                .hasElements()
                .flatMap(exists -> {
                    if (exists) return Mono.error(DuplicatedSsnException::new);
                    return Mono.empty();
                });
    }

    private Mono<Void>  onDuplicatedEmail(String email) {
        return doctorRepository
                .findAll()
                .filter(c -> c.getPerson().getEmail().equals(email))
                .hasElements()
                .flatMap(exists -> {
                    if (exists) return Mono.error(DuplicatedEmailException::new);
                    return Mono.empty();
                });
    }
}
