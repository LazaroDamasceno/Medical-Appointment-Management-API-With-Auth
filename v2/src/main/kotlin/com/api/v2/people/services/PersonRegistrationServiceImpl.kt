package com.api.v2.people.services

import com.api.v2.people.domain.exposed.Person
import com.api.v2.people.domain.PersonRepository
import com.api.v2.people.requests.PersonRegistrationDto
import com.api.v2.people.services.exposed.PersonRegistrationService
import jakarta.validation.Valid
import org.springframework.stereotype.Service

@Service
class PersonRegistrationServiceImpl(private val repository: PersonRepository) : PersonRegistrationService {

    override suspend fun register(registrationDto: @Valid PersonRegistrationDto): Person {
        val foundPerson = repository.findBySinOrEmail(registrationDto.sin, registrationDto.email);
        if (foundPerson == null) {
            val newPerson = Person.of(registrationDto)
            return repository.save(newPerson)
        }
        return foundPerson
    }
}