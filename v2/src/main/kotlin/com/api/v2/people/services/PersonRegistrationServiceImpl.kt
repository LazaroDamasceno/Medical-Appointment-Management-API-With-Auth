package com.api.v2.people.services

import com.api.v2.people.Person
import com.api.v2.people.PersonRegistrationService
import com.api.v2.people.domain.PersonCrudRepository
import com.api.v2.people.requests.PersonRegistrationDTO
import jakarta.validation.Valid
import org.springframework.stereotype.Service

@Service
class PersonRegistrationServiceImpl(val personCrudRepository: PersonCrudRepository): PersonRegistrationService {

    override fun register(registrationDTO: @Valid PersonRegistrationDTO): Person {
        val foundPerson = personCrudRepository.findBySINOrEmail(registrationDTO.sin, registrationDTO.email)
        if (foundPerson == null) {
            val newPerson = Person.of(registrationDTO)
            return personCrudRepository.save(newPerson)
        }
        return foundPerson
    }
}