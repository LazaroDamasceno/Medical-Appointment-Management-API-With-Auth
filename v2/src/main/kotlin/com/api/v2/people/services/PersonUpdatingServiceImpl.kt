package com.api.v2.people.services

import com.api.v2.people.domain.PersonAuditTrail
import com.api.v2.people.domain.PersonAuditTrailRepository
import com.api.v2.people.domain.PersonRepository
import com.api.v2.people.domain.exposed.Person
import com.api.v2.people.requests.PersonUpdatingDTO
import com.api.v2.people.services.exposed.PersonUpdatingService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.stereotype.Service

@Service
class PersonUpdatingServiceImpl(
    private val auditTrailRepository: PersonAuditTrailRepository,
    private val repository: PersonRepository
) : PersonUpdatingService {

    override suspend fun update(
        person: @NotNull Person,
        updatingDTO: @Valid PersonUpdatingDTO
    ): Person {
        val auditTrail = PersonAuditTrail.of(person)
        val savedAuditTrail = auditTrailRepository.save(auditTrail)
        person.update(updatingDTO)
        return repository.save(person)
    }
}