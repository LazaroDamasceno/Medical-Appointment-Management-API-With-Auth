package com.api.v2.people.services

import com.api.v2.people.domain.exposed.Person
import com.api.v2.people.domain.PersonRepository
import com.api.v2.people.requests.PersonRegistrationDTO
import com.api.v2.people.services.exposed.PersonRegistrationService
import jakarta.validation.Valid
import org.springframework.stereotype.Service

@Service
class PersonRegistrationServiceImpl(private val personRepository: PersonRepository) : PersonRegistrationService {

    override suspend fun register(registrationDTO: @Valid PersonRegistrationDTO): Person {
        val foundPerson = personRepository.findBySINOrEmail(registrationDTO.sin, registrationDTO.email)
        if (foundPerson == null) {
            val newPerson = Person.of(registrationDTO)
            return personRepository.save(newPerson)
        }
        return foundPerson
    }
}