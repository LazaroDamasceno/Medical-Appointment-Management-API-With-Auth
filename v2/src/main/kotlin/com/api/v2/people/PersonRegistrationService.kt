package com.api.v2.people

interface PersonRegistrationService {
    fun register(registrationDTO: PersonRegistrationDTO): Person
}