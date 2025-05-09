package com.api.v1.nurses.domain;

import com.api.v1.nurses.domain.exposed.Nurse;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface NurseRepository extends ReactiveMongoRepository<Nurse, String> {
}
