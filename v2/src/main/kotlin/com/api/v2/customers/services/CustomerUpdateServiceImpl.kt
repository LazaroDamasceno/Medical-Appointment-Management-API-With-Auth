package com.api.v2.customers.services

import com.api.v2.customers.Customer
import com.api.v2.customers.domain.CustomerAuditRepository
import com.api.v2.customers.domain.CustomerAuditTrail
import com.api.v2.customers.domain.CustomerCrudRepository
import com.api.v2.customers.CustomerFinder
import com.api.v2.people.PersonUpdateDTO
import com.api.v2.people.PersonUpdateService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CustomerUpdateServiceImpl(
    val crudRepository: CustomerCrudRepository,
    val auditRepository: CustomerAuditRepository,
    val personUpdateService: PersonUpdateService,
    val customerFinder: CustomerFinder
) : CustomerUpdateService {

    override fun update(customerId: String, updateDTO: @Valid PersonUpdateDTO): ResponseEntity<Unit> {
        val foundCustomer = customerFinder.findById(customerId)
        val auditTrail = CustomerAuditTrail.of(foundCustomer)
        val savedAuditTrail = auditRepository.save(auditTrail)
        val updatedPerson = personUpdateService.update(foundCustomer.person, updateDTO)
        val updatedCustomer = foundCustomer.update(updatedPerson)
        val savedCustomer = crudRepository.save(updatedCustomer)
        return ResponseEntity.noContent().build()
    }
}