package com.api.v1.nurses.services;

import com.api.v1.nurses.domain.NurseCrudRepository;
import com.api.v1.nurses.domain.exposed.Nurse;
import com.api.v1.nurses.exceptions.DuplicatedNurseLicenseNumberException;
import com.api.v1.nurses.requests.NurseRegistrationDTO;
import com.api.v1.nurses.responses.NurseResponseDTO;
import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.exceptions.DuplicatedEmailException;
import com.api.v1.people.exceptions.DuplicatedSINException;
import com.api.v1.people.services.exposed.PersonRegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class NurseRegistrationServiceImpl implements NurseRegistrationService {

    private final NurseCrudRepository crudRepository;
    private final PersonRegistrationService personRegistrationService;

    public NurseRegistrationServiceImpl(NurseCrudRepository crudRepository,
                                        PersonRegistrationService personRegistrationService
    ) {
        this.crudRepository = crudRepository;
        this.personRegistrationService = personRegistrationService;
    }

    @Override
    public ResponseEntity<NurseResponseDTO> register(@Valid NurseRegistrationDTO registrationDTO) {
        validate(registrationDTO);
        Person savedPerson = personRegistrationService.register(registrationDTO.person());
        Nurse newNurse = Nurse.of(savedPerson);
        Nurse savedNurse = crudRepository.save(newNurse);
        NurseResponseDTO dto = savedNurse.toDTO();
        return ResponseEntity
                .created(URI.create("/api/v1/nurses"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto);
    }

    public void validate(NurseRegistrationDTO registrationDTO) {
        if (crudRepository.findByLicenseNumber(registrationDTO.licenseNumber()).isPresent()) {
            throw new DuplicatedNurseLicenseNumberException();
        }
        if (crudRepository.findBySIN(registrationDTO.person().sin()).isPresent()) {
            throw new DuplicatedSINException();
        }
        if (crudRepository.findByEmail(registrationDTO.person().email()).isPresent()) {
            throw new DuplicatedEmailException();
        }
    }
}
