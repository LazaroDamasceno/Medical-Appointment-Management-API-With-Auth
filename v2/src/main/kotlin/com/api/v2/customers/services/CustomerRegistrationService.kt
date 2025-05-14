package com.api.v2.customers.services

import com.api.v2.common.ResultData
import com.api.v2.customers.response.CustomerResponseDto
import com.api.v2.people.requests.PersonRegistrationDto
import org.springframework.http.ResponseEntity

interface CustomerRegistrationService {
    suspend fun register(registrationDto: PersonRegistrationDto): ResponseEntity<ResultData<CustomerResponseDto>>
}