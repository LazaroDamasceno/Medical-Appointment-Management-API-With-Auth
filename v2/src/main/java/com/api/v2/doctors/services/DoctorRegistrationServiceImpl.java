package com.api.v2.doctors.services;

import com.api.v2.doctors.domain.MedicalLicenseNumber;
import com.api.v2.doctors.dtos.DoctorRepository;
import com.api.v2.doctors.requests.DoctorRegistrationDto;
import com.api.v2.doctors.responses.DoctorResponseDto;
import com.api.v2.people.exceptions.DuplicatedEmailException;
import com.api.v2.people.exceptions.DuplicatedSsnException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DoctorRegistrationServiceImpl implements DoctorRegistrationService {

    private final DoctorRepository doctorRepository;

    @Override
    public Mono<ResponseEntity<DoctorResponseDto>> register(@Valid DoctorRegistrationDto registrationDto) {
        return null;
    }

    private Mono<ResponseEntity<DoctorResponseDto>> validate(String ssn, String email, MedicalLicenseNumber medicalLicenseNumber) {
        return doctorRepository
                .findBySsn(ssn)
                .switchIfEmpty(Mono.empty())
                .flatMap(_ -> Mono.error(DuplicatedSsnException::new))
                .then(doctorRepository
                        .findByEmail(email)
                        .switchIfEmpty(Mono.empty())
                        .flatMap(_ -> Mono.error(DuplicatedEmailException::new))
                )
                .then(doctorRepository
                        .findByMedicalLicenseNumber(medicalLicenseNumber)
                        .switchIfEmpty(Mono.empty())
                        .flatMap(_ -> Mono.error(DuplicatedEmailException::new))
                );
    }
}
