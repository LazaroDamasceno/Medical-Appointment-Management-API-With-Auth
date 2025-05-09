package com.api.v1.nurses;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface NurseRepository extends ReactiveMongoRepository<Nurse, String> {
}
