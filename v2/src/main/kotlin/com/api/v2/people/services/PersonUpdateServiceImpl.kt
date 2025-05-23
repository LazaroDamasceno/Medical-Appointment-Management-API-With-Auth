package com.api.v2.people.services

import com.api.v2.people.domain.PersonAuditTrail
import com.api.v2.people.domain.PersonAuditTrailRepository
import com.api.v2.people.domain.PersonRepository
import com.api.v2.people.domain.exposed.Person
import com.api.v2.people.requests.PersonUpdateDTO
import com.api.v2.people.services.exposed.PersonUpdateService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.stereotype.Service

@Service
class PersonUpdateServiceImpl: PersonUpdateService {

    private val auditTrailRepository: PersonAuditTrailRepository
    private val repository: PersonRepository

    constructor(auditTrailRepository: PersonAuditTrailRepository,
                repository: PersonRepository
    ) {
        this.auditTrailRepository = auditTrailRepository
        this.repository = repository
    }

    override suspend fun update(
        person: @NotNull Person,
        updatingDTO: @Valid PersonUpdateDTO
    ): Person {
        val auditTrail = PersonAuditTrail.of(person)
        val savedAuditTrail = auditTrailRepository.save(auditTrail)
        person.update(updatingDTO)
        return repository.save(person)
    }
}