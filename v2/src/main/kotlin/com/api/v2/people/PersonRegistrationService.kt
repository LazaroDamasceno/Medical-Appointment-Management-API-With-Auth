package com.api.v2.people

import com.api.v2.people.Person
import com.api.v2.people.requests.PersonRegistrationDTO

interface PersonRegistrationService {
    fun register(registrationDTO: PersonRegistrationDTO): Person
}