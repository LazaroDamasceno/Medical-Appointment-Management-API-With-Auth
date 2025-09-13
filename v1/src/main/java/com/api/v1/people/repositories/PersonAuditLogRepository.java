package com.api.v1.people.repositories;

import com.api.v1.people.models.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonAuditLogRepository extends MongoRepository<Person, String> {
}
