package com.api.v2.customers.services

import com.api.v2.customers.domain.exposed.Customer
import com.api.v2.customers.domain.CustomerAuditRepository
import com.api.v2.customers.domain.CustomerAuditTrail
import com.api.v2.customers.domain.CustomerCrudRepository
import com.api.v2.customers.utils.exposed.CustomerFinder
import com.api.v2.people.requests.PersonUpdateDTO
import com.api.v2.people.services.exposed.PersonUpdateService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CustomerUpdateServiceImpl: CustomerUpdateService {

    private val crudRepository: CustomerCrudRepository
    private val auditRepository: CustomerAuditRepository
    private val personUpdateService: PersonUpdateService
    private val customerFinder: CustomerFinder

    constructor(
        crudRepository: CustomerCrudRepository,
        auditRepository: CustomerAuditRepository,
        personUpdateService: PersonUpdateService,
        customerFinder: CustomerFinder
    ) {
        this.crudRepository = crudRepository
        this.auditRepository = auditRepository
        this.personUpdateService = personUpdateService
        this.customerFinder = customerFinder
    }

    override fun update(customerId: String, updateDTO: @Valid PersonUpdateDTO): ResponseEntity<Unit> {
        val foundCustomer = customerFinder.findById(customerId)
        val auditTrail = CustomerAuditTrail.of(foundCustomer)
        val savedAuditTrail = auditRepository.save<CustomerAuditTrail>(auditTrail)
        val updatedPerson = personUpdateService.update(foundCustomer.person, updateDTO)
        foundCustomer.update(updatedPerson)
        val updatedCustomer = crudRepository.save<Customer>(foundCustomer)
        return ResponseEntity.noContent().build()
    }
}