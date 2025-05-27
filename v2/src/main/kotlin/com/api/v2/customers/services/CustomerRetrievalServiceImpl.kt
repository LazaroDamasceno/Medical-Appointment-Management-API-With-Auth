package com.api.v2.customers.services

import com.api.v2.customers.controllers.CustomerController
import com.api.v2.customers.domain.exposed.Customer
import com.api.v2.customers.domain.CustomerCrudRepository
import com.api.v2.customers.responses.CustomerResponseDTO
import com.api.v2.customers.utils.exposed.CustomerFinder
import com.api.v2.customers.utils.exposed.toDTO
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

    override fun findById(id: String): ResponseEntity<CustomerResponseDTO> {
        val foundCustomer = customerFinder.findById(id)
        val dto = foundCustomer.toDTO()
        dto.add(
            linkTo(methodOn(CustomerController::class.java).findById(id)).withSelfRel()
        )
        return ResponseEntity.ok(dto)
    }

    override fun findAll(pageable: Pageable): ResponseEntity<List<CustomerResponseDTO>> {
        val response = crudRepository
            .findAll()
            .map(Customer::toDTO)
        return ResponseEntity.ok(response)
    }
}