package com.api.v2.people.services

import com.api.v2.people.domain.Person
import com.api.v2.people.domain.PersonAuditTrail
import com.api.v2.people.domain.PersonAuditTrailRepository
import com.api.v2.people.domain.PersonRepository
import com.api.v2.people.requests.PersonUpdatingDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class PersonUpdateServiceImpl(
    private val repository: PersonRepository,
    private val personAuditTrail: PersonAuditTrailRepository
) : PersonUpdateService {

    override suspend fun update(
        person: Person,
        updatingDto: PersonUpdatingDto
    ): Person {
        return withContext(Dispatchers.IO) {
            val auditTrail = PersonAuditTrail.of(person)
            personAuditTrail.save(auditTrail)
            person.update(updatingDto)
            repository.save(person)
        }
    }
}