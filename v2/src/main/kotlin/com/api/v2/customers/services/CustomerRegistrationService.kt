package com.api.v2.customers.services

import com.api.v2.customers.CustomerResponseDTO
import com.api.v2.people.requests.PersonRegistrationDTO
import org.springframework.http.ResponseEntity

interface CustomerRegistrationService {
    fun register(registrationDTO: PersonRegistrationDTO): ResponseEntity<CustomerResponseDTO>
}