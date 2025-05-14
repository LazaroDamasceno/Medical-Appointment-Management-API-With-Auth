package com.api.v2.people.services.exposed

import com.api.v2.people.domain.exposed.Person
import com.api.v2.people.requests.PersonRegistrationDto

interface PersonRegistrationService {
    suspend fun register(registrationDto: PersonRegistrationDto): Person
}