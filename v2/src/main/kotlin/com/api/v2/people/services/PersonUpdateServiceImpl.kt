package com.api.v2.people.services

import com.api.v2.people.domain.exposed.Person
import com.api.v2.people.domain.PersonAuditTrail
import com.api.v2.people.domain.PersonAuditTrailRepository
import com.api.v2.people.domain.PersonRepository
import com.api.v2.people.requests.PersonUpdatingDto
import com.api.v2.people.services.exposed.PersonUpdateService
import jakarta.validation.Valid
import org.jetbrains.annotations.NotNull
import org.springframework.stereotype.Service

@Service
class PersonUpdateServiceImpl(
    private val repository: PersonRepository,
    private val personAuditTrail: PersonAuditTrailRepository
) : PersonUpdateService {

    override suspend fun update(
        @NotNull person: Person,
        @Valid updatingDto: PersonUpdatingDto
    ): Person {
        val auditTrail = PersonAuditTrail.of(person)
        personAuditTrail.save(auditTrail)
        person.update(updatingDto)
        return repository.save(person)
    }
}