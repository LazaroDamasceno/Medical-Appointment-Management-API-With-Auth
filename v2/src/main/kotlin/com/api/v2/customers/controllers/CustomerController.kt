package com.api.v2.customers.controllers

import com.api.v2.common.ResultData
import com.api.v2.customers.response.CustomerResponseDto
import com.api.v2.customers.services.CustomerRegistrationService
import com.api.v2.people.requests.PersonRegistrationDto
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v2/customers")
class CustomerController(
    private val registrationService: CustomerRegistrationService
) {

    @PostMapping
    suspend fun register(
        @RequestBody registrationDto: @Valid PersonRegistrationDto
    ): ResponseEntity<ResultData<CustomerResponseDto>> {
        return registrationService.register(registrationDto)
    }
}