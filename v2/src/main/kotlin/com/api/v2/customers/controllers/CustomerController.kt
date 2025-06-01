package com.api.v2.customers.controllers

import com.api.v2.customers.CustomerResponseDTO
import com.api.v2.customers.services.*
import com.api.v2.people.PersonRegistrationDTO
import com.api.v2.people.PersonUpdateDTO
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v2/customers")
class CustomerController(
    private val registrationService: CustomerRegistrationService,
    private val updateService: CustomerUpdateService,
    private val retrievalService: CustomerRetrievalService
) {

    @PostMapping
    fun register(@RequestBody registrationDTO: @Valid PersonRegistrationDTO): ResponseEntity<CustomerResponseDTO> {
        return registrationService.register(registrationDTO)
    }

    @PatchMapping("{customerId}")
    fun update(
        @PathVariable customerId: String,
        @RequestBody updateDTO: @Valid PersonUpdateDTO
    ): ResponseEntity<Unit> {
        return updateService.update(customerId, updateDTO)
    }

    @GetMapping("{id}")
    fun findById(@PathVariable id: String): ResponseEntity<CustomerResponseDTO> {
        return retrievalService.findById(id)
    }

    @GetMapping
    fun findAll(pageable: Pageable): ResponseEntity<Page<CustomerResponseDTO>> {
        return retrievalService.findAll(pageable)
    }

}