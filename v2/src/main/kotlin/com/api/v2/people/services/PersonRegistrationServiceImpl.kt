package com.api.v2.people.services

import com.api.v2.people.domain.Person
import com.api.v2.people.domain.PersonRepository
import com.api.v2.people.requests.PersonRegistrationDto
import jakarta.validation.Valid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class PersonRegistrationServiceImpl(private val repository: PersonRepository) : PersonRegistrationService {

    override suspend fun register(registrationDto: @Valid PersonRegistrationDto): Person {
        return withContext(Dispatchers.IO) {
            val foundPerson = repository.findBySinOrEmail(registrationDto.sin, registrationDto.email);
            if (foundPerson != null) {
                foundPerson;
            }
            val newPerson = Person.of(registrationDto)
            repository.save(newPerson)
        }
    }
}