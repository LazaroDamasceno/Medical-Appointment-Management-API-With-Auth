package com.api.v1.doctors.services;

import com.api.v1.doctors.domain.MedicalLicenseNumber;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.domain.DoctorRepository;
import com.api.v1.doctors.exceptions.DuplicatedMedicalLicenseNumberException;
import com.api.v1.doctors.requests.DoctorRegistrationDto;
import com.api.v1.doctors.responses.DoctorResponseDto;
import com.api.v1.people.exceptions.DuplicatedEmailException;
import com.api.v1.people.exceptions.DuplicatedSsnException;
import com.api.v1.people.requests.PersonRegistrationDto;
import com.api.v1.people.services.exposed.PersonRegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class DoctorRegistrationServiceImpl implements DoctorRegistrationService {

    private final DoctorRepository doctorRepository;
    private final PersonRegistrationService personRegistrationService;

    public DoctorRegistrationServiceImpl(DoctorRepository doctorRepository,
                                         PersonRegistrationService personRegistrationService
    ) {
        this.doctorRepository = doctorRepository;
        this.personRegistrationService = personRegistrationService;
    }

    @Override
    public Mono<ResponseEntity<DoctorResponseDto>> register(@Valid DoctorRegistrationDto registrationDto) {
        return validate(registrationDto.person(), registrationDto.medicalLicenseNumber())
                .then(personRegistrationService.register(registrationDto.person())
                .flatMap(person -> {
                    Doctor doctor = Doctor.of(registrationDto.medicalLicenseNumber(), person, registrationDto.medicalSpeciality());
                    return doctorRepository
                            .save(doctor)
                            .flatMap(savedDoctor -> {
                                DoctorResponseDto responseDto = savedDoctor.toDto();
                                ResponseEntity<DoctorResponseDto> responseEntity = ResponseEntity
                                        .created(URI.create("/api/v1/doctors"))
                                        .body(responseDto);
                                return Mono.just(responseEntity);
                            });
                }));
    }

    private Mono<Object> validate(PersonRegistrationDto personRegistrationDto,
                                                             MedicalLicenseNumber medicalLicenseNumber
    ) {
        return doctorRepository
                .findBySsn(personRegistrationDto.sin())
                .switchIfEmpty(Mono.empty())
                .flatMap(_ -> Mono.error(DuplicatedSsnException::new))
                .then(doctorRepository
                        .findByEmail(personRegistrationDto.email())
                        .switchIfEmpty(Mono.empty())
                        .flatMap(_ -> Mono.error(DuplicatedEmailException::new))
                )
                .then(doctorRepository
                        .findByMedicalLicenseNumber(medicalLicenseNumber)
                        .switchIfEmpty(Mono.empty())
                        .flatMap(_ -> Mono.error(DuplicatedMedicalLicenseNumberException::new))
                );
    }
}
