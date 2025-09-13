package com.api.v1.physicians.repositories;

import com.api.v1.physicians.models.Physician;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhysicianAuditLogRepository extends MongoRepository<Physician, String> {
}
