package com.api.v2.customers.services

import com.api.v2.customers.domain.Customer
import com.api.v2.customers.domain.CustomerCrudRepository
import com.api.v2.customers.responses.CustomerResponseDTO
import com.api.v2.people.exceptions.DuplicatedSINException
import com.api.v2.people.requests.PersonRegistrationDTO
import com.api.v2.people.services.exposed.PersonRegistrationService
import jakarta.validation.Valid
import org.springframework.stereotype.Service

@Service
class CustomerRegistrationServiceImpl(
    private val repository: CustomerCrudRepository,
    private val personRegistrationService: PersonRegistrationService
) : CustomerRegistrationService {

    override suspend fun register(registrationDTO: @Valid PersonRegistrationDTO): CustomerResponseDTO {
        validate(registrationDTO)
        val savedPerson = personRegistrationService.register(registrationDTO)
        val newCustomer = Customer.update(savedPerson)
        val savedCustomer = repository.save<Customer>(newCustomer)
        return savedCustomer.toDTO()
    }

    private suspend fun validate(registrationDTO: PersonRegistrationDTO) {
        if (repository.findBySIN(registrationDTO.sin) != null) {
            throw DuplicatedSINException()
        }
        if (repository.findByEmail(registrationDTO.email) != null) {
            throw DuplicatedSINException()
        }
    }
}