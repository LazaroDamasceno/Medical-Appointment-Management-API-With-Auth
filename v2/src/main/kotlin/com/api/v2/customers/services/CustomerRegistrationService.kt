package com.api.v2.customers.services

import com.api.v2.customers.responses.CustomerResponseDTO
import com.api.v2.people.requests.PersonRegistrationDTO

interface CustomerRegistrationService {
    suspend fun register(registrationDTO: PersonRegistrationDTO): CustomerResponseDTO
}