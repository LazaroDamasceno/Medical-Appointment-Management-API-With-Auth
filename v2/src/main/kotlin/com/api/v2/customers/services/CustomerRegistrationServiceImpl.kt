package com.api.v2.customers.services

import com.api.v2.customers.Customer
import com.api.v2.customers.domain.CustomerCrudRepository
import com.api.v2.customers.CustomerResponseDTO
import com.api.v2.people.DuplicatedSINException
import com.api.v2.people.requests.PersonRegistrationDTO
import com.api.v2.people.PersonRegistrationService
import com.api.v2.customers.toDTO
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.net.URI

@Service
class CustomerRegistrationServiceImpl(
    val repository: CustomerCrudRepository,
    val personRegistrationService: PersonRegistrationService
) : CustomerRegistrationService {

    override fun register(registrationDTO: @Valid PersonRegistrationDTO): ResponseEntity<CustomerResponseDTO> {
        validate(registrationDTO)
        val savedPerson = personRegistrationService.register(registrationDTO)
        val newCustomer = Customer.update(savedPerson)
        val savedCustomer = repository.save<Customer>(newCustomer)
        val dto = savedCustomer.toDTO()
        return ResponseEntity
            .created(URI.create("/api/v2/customers"))
            .contentType(MediaType.APPLICATION_JSON)
            .body(dto)
    }

    private fun validate(registrationDTO: PersonRegistrationDTO) {
        if (repository.findBySIN(registrationDTO.sin) != null) {
            throw DuplicatedSINException()
        }
        if (repository.findByEmail(registrationDTO.email) != null) {
            throw DuplicatedSINException()
        }
    }
}