package com.api.v1.nurses.domain;

import com.api.v1.nurses.domain.exposed.Nurse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NurseCrudRepository extends MongoRepository<Nurse, String> {
}
