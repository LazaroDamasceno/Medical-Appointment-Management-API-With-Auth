package com.api.v1.doctors.services;

import com.api.v1.doctors.domain.DoctorRepository;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.exceptions.DuplicatedMedicalLicenseNumberException;
import com.api.v1.doctors.requests.DoctorRegistrationDTO;
import com.api.v1.doctors.response.DoctorResponseDto;
import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.exceptions.DuplicatedSINException;
import com.api.v1.people.services.exposed.PersonRegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class DoctorRegistrationServiceImpl implements DoctorRegistrationService {

    private final DoctorRepository repository;
    private final PersonRegistrationService personRegistrationService;

    public DoctorRegistrationServiceImpl(DoctorRepository repository,
                                         PersonRegistrationService personRegistrationService
    ) {
        this.repository = repository;
        this.personRegistrationService = personRegistrationService;
    }

    @Override
    public ResponseEntity<DoctorResponseDto> register(@Valid DoctorRegistrationDTO registrationDto) {
        validate(registrationDto);
        Person savedPerson = personRegistrationService.register(registrationDto.person());
        Doctor newDoctor = Doctor.of(savedPerson, registrationDto.licenseNumber());
        Doctor savedDoctor = repository.save(newDoctor);
        DoctorResponseDto responseDto = savedDoctor.toDto();
        return ResponseEntity
                .created(URI.create("/api/v1/doctors"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDto);
    }

    private void validate(DoctorRegistrationDTO registrationDto) {
        if (repository.findByLicenseNumber(registrationDto.licenseNumber()).isPresent()) {
            throw new DuplicatedMedicalLicenseNumberException(registrationDto.licenseNumber());
        }
        if (repository.findBySIN(registrationDto.person().sin()).isPresent()) {
            throw new DuplicatedSINException();
        }
        if (repository.findByEmail(registrationDto.person().email()).isPresent()) {
            throw new DuplicatedSINException();
        }
    }
}
