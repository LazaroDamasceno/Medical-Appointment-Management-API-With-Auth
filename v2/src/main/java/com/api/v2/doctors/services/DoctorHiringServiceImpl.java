package com.api.v2.doctors.services;

import com.api.v2.doctors.domain.Doctor;
import com.api.v2.doctors.domain.DoctorRepository;
import com.api.v2.doctors.dtos.DoctorHiringDto;
import com.api.v2.doctors.dtos.DoctorResponseDto;
import com.api.v2.people.exceptions.DuplicatedEmailException;
import com.api.v2.doctors.exceptions.DuplicatedMedicalLicenseNumberException;
import com.api.v2.people.exceptions.DuplicatedSsnException;
import com.api.v2.doctors.utils.DoctorResponseMapper;
import com.api.v2.people.services.interfaces.PersonRegistrationService;
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
        return onDuplicatedMedicalLicenseNumber(hiringDto.medicalLicenseNumber())
                .then(onDuplicatedSsn(hiringDto.person().ssn()))
                .then(onDuplicatedEmail(hiringDto.person().email()))
                .then(Mono.defer(() -> {
                    return personRegistrationService
                            .register(hiringDto.person())
                            .flatMap(person -> {
                                Doctor doctor = Doctor.create(hiringDto.medicalLicenseNumber(), person);
                                return doctorRepository.save(doctor);
                            })
                            .flatMap(DoctorResponseMapper::mapToMono);
                }));
    }

    private Mono<Void> onDuplicatedMedicalLicenseNumber(String medicalLicenseNumber) {
        return doctorRepository
                .findAll()
                .filter(d -> d.getLicenseNumber().equals(medicalLicenseNumber))
                .singleOrEmpty()
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
