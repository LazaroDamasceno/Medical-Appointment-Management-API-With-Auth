package com.api.v1.doctors.services;

import com.api.v1.doctors.domain.DoctorAuditTrail;
import com.api.v1.doctors.domain.DoctorAuditTrailRepository;
import com.api.v1.doctors.domain.DoctorRepository;
import com.api.v1.doctors.utils.DoctorFinder;
import com.api.v1.people.requests.PersonUpdatingDto;
import com.api.v1.people.services.exposed.PersonUpdatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DoctorUpdatingServiceImpl implements DoctorUpdatingService {

    private final PersonUpdatingService personUpdatingService;
    private final DoctorRepository doctorRepository;
    private final DoctorAuditTrailRepository doctorAuditTrailRepository;
    private final DoctorFinder doctorFinder;

    @Override
    public Mono<ResponseEntity<Void>> update(String doctorId, @Valid PersonUpdatingDto updatingDto) {
        return doctorFinder
                .findById(doctorId)
                .flatMap(foundDoctor -> {
                    return personUpdatingService.update(foundDoctor.getPerson(), updatingDto)
                            .flatMap(updatedPerson -> {
                                DoctorAuditTrail auditTrail = DoctorAuditTrail.of(foundDoctor);
                                return doctorAuditTrailRepository
                                        .save(auditTrail)
                                        .flatMap(_ -> {
                                            foundDoctor.update(updatedPerson);
                                            return doctorRepository.save(foundDoctor);
                                        });
                            });
                })
                .flatMap(_ -> {
                    ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();
                    return Mono.just(responseEntity);
                });
    }
}
