package com.api.v1;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhysicianRepository extends MongoRepository<String, Physician> {
}
