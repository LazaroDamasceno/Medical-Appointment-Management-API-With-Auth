package com.api.v2.customers.services

import com.api.v2.customers.domain.Customer
import com.api.v2.customers.domain.CustomerCrudRepository
import com.api.v2.customers.responses.CustomerResponseDTO
import com.api.v2.customers.utils.exposed.CustomerFinder
import com.api.v2.toDTO
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CustomerRetrievalServiceImpl: CustomerRetrievalService {

    private val crudRepository: CustomerCrudRepository
    private val customerFinder: CustomerFinder

    constructor(
        crudRepository: CustomerCrudRepository,
        customerFinder: CustomerFinder
    ) {
        this.crudRepository = crudRepository
        this.customerFinder = customerFinder
    }

    override suspend fun findById(id: String): ResponseEntity<CustomerResponseDTO> {
        val foundCustomer = customerFinder.findById(id)
        val dto = foundCustomer.toDTO()
        return ResponseEntity.ok(dto)
    }

    override suspend fun findAll(pageable: Pageable): ResponseEntity<List<CustomerResponseDTO>> {
        val response = crudRepository
            .findAll()
            .map(Customer::toDTO)
        return ResponseEntity.ok(response)
    }
}