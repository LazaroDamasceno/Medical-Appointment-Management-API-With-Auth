package com.api.v1.nurses.domain;

import com.api.v1.nurses.domain.exposed.Nurse;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface NurseRepository extends ReactiveMongoRepository<Nurse, String> {

    @Query("{ 'licenseNumber: ?0 }")
    Mono<Nurse> findByLicenseNumber(String licenseNumber);

    @Query("{ 'person.ssn: ?0 }")
    Mono<Nurse> findBySsn(String ssn);

    @Query("{ 'person.email: ?0 }")
    Mono<Nurse> findByEmail(String email);
}
