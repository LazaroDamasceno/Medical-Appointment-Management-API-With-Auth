package com.api.v2.people.services

import com.api.v2.people.domain.PersonAuditTrail
import com.api.v2.people.domain.PersonAuditRepository
import com.api.v2.people.domain.PersonCrudRepository
import com.api.v2.people.Person
import com.api.v2.people.PersonUpdateService
import com.api.v2.people.requests.PersonUpdateDTO
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.stereotype.Service

@Service
class PersonUpdateServiceImpl(
    private val auditTrailRepository: PersonAuditRepository,
    private val repository: PersonCrudRepository
) : PersonUpdateService {

    override fun update(
        person: @NotNull Person,
        updatingDTO: @Valid PersonUpdateDTO
    ): Person {
        val auditTrail = PersonAuditTrail.of(person)
        val savedAuditTrail = auditTrailRepository.save(auditTrail)
        person.update(updatingDTO)
        return repository.save(person)
    }
}