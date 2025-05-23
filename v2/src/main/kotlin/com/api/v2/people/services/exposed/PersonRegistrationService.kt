package com.api.v2.people.services.exposed

import com.api.v2.people.domain.exposed.Person
import com.api.v2.people.requests.PersonRegistrationDTO

interface PersonRegistrationService {
    fun register(registrationDTO: PersonRegistrationDTO): Person
}