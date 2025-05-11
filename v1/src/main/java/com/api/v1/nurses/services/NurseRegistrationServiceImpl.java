package com.api.v1.nurses.services;

import com.api.v1.nurses.domain.NurseRepository;
import com.api.v1.nurses.domain.exposed.Nurse;
import com.api.v1.nurses.exceptions.DuplicatedNurseLicenseNumberException;
import com.api.v1.nurses.responses.NurseResponseDto;
import com.api.v1.nurses.resquests.NurseRegistrationDto;
import com.api.v1.people.exceptions.*;
import com.api.v1.people.services.exposed.PersonRegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class NurseRegistrationServiceImpl implements NurseRegistrationService {

    private final NurseRepository nurseRepository;
    private final PersonRegistrationService personRegistrationService;

    public NurseRegistrationServiceImpl(NurseRepository nurseRepository,
                                        PersonRegistrationService personRegistrationService
    ) {
        this.nurseRepository = nurseRepository;
        this.personRegistrationService = personRegistrationService;
    }

    @Override
    public Mono<ResponseEntity<NurseResponseDto>> register(@Valid NurseRegistrationDto registrationDto) {
        return validated(registrationDto)
                .then(Mono.defer(() -> {
                    return personRegistrationService
                            .register(registrationDto.person())
                            .flatMap(savedPerson -> {
                                Nurse nurse = Nurse.of(savedPerson, registrationDto.licenseNumber());
                                return nurseRepository.save(nurse);
                            })
                            .map(Nurse::toDto)
                            .map(responseDto -> {
                                return ResponseEntity
                                        .created(URI.create("/api/v1/nurses"))
                                        .body(responseDto);
                            });
                }));
    }

    private Mono<Object> validated(NurseRegistrationDto registrationDto) {
        return nurseRepository
                .findBySsn(registrationDto.person().sin())
                .switchIfEmpty(Mono.empty())
                .flatMap(_ -> Mono.error(new DuplicatedSinException()))
                .then(nurseRepository
                        .findByEmail(registrationDto.person().email())
                        .switchIfEmpty(Mono.empty())
                        .flatMap(_ -> Mono.error(new DuplicatedEmailException()))
                )
                .then(nurseRepository
                        .findByLicenseNumber(registrationDto.licenseNumber())
                        .switchIfEmpty(Mono.empty())
                        .flatMap(_ -> Mono.error(new DuplicatedNurseLicenseNumberException(registrationDto.licenseNumber())))
                );
    }
}
