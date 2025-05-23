package com.api.v2.customers.controllers

import com.api.v2.customers.responses.CustomerResponseDTO
import com.api.v2.customers.services.*
import com.api.v2.people.requests.PersonRegistrationDTO
import com.api.v2.people.requests.PersonUpdateDTO
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v2/customers")
class CustomerController {

    private val registrationService: CustomerRegistrationService
    private val updateService: CustomerUpdateService
    private val retrievalService: CustomerRetrievalService

    constructor(
        registrationService: CustomerRegistrationService,
        updateService: CustomerUpdateService,
        retrievalService: CustomerRetrievalService
    ) {
        this.registrationService = registrationService
        this.updateService = updateService
        this.retrievalService = retrievalService
    }

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
    fun findAll(@RequestParam pageable: Pageable): ResponseEntity<List<CustomerResponseDTO>> {
        return retrievalService.findAll(pageable)
    }

}