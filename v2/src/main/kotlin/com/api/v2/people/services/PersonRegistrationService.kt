package com.api.v2.people.services

import com.api.v2.people.domain.Person
import com.api.v2.people.requests.PersonRegistrationDTO

interface PersonRegistrationService {
    suspend fun register(registrationDTO: PersonRegistrationDTO): Person
}