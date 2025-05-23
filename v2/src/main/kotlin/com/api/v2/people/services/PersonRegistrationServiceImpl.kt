package com.api.v2.people.services

import com.api.v2.people.domain.exposed.Person
import com.api.v2.people.domain.PersonCrudRepository
import com.api.v2.people.requests.PersonRegistrationDTO
import com.api.v2.people.services.exposed.PersonRegistrationService
import jakarta.validation.Valid
import org.springframework.stereotype.Service

@Service
class PersonRegistrationServiceImpl: PersonRegistrationService {

    private val personCrudRepository: PersonCrudRepository

    constructor(personCrudRepository: PersonCrudRepository) {
        this.personCrudRepository = personCrudRepository
    }

    override fun register(registrationDTO: @Valid PersonRegistrationDTO): Person {
        val foundPerson = personCrudRepository.findBySINOrEmail(registrationDTO.sin, registrationDTO.email)
        if (foundPerson == null) {
            val newPerson = Person.of(registrationDTO)
            return personCrudRepository.save(newPerson)
        }
        return foundPerson
    }
}