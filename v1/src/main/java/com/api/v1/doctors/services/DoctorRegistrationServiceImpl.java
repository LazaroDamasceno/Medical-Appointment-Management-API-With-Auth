package com.api.v1.doctors.services;

import com.api.v1.common.DuplicatedLicenseNumberException;
import com.api.v1.doctors.domain.DoctorCrudRepository;
import com.api.v1.doctors.Doctor;
import com.api.v1.doctors.requests.DoctorRegistrationDTO;
import com.api.v1.doctors.DoctorResponseDto;
import com.api.v1.people.Person;
import com.api.v1.people.DuplicatedSINException;
import com.api.v1.people.PersonRegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class DoctorRegistrationServiceImpl implements DoctorRegistrationService {

    private final DoctorCrudRepository repository;
    private final PersonRegistrationService personRegistrationService;

    public DoctorRegistrationServiceImpl(DoctorCrudRepository repository,
                                         PersonRegistrationService personRegistrationService
    ) {
        this.repository = repository;
        this.personRegistrationService = personRegistrationService;
    }

    @Override
    public ResponseEntity<DoctorResponseDto> register(@Valid DoctorRegistrationDTO registrationDTO) {
        validate(registrationDTO);
        Person savedPerson = personRegistrationService.register(registrationDTO.person());
        Doctor newDoctor = Doctor.of(savedPerson, registrationDTO.licenseNumber());
        Doctor savedDoctor = repository.save(newDoctor);
        DoctorResponseDto responseDto = savedDoctor.toDto();
        return ResponseEntity
                .created(URI.create("/api/v1/doctors"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDto);
    }

    private void validate(DoctorRegistrationDTO registrationDto) {
        if (repository.findByLicenseNumber(registrationDto.licenseNumber()).isPresent()) {
            throw new DuplicatedLicenseNumberException();
        }
        if (repository.findBySIN(registrationDto.person().sin()).isPresent()) {
            throw new DuplicatedSINException();
        }
        if (repository.findByEmail(registrationDto.person().email()).isPresent()) {
            throw new DuplicatedSINException();
        }
    }
}
