package com.api.v2.customers.utils

import com.api.v2.customers.domain.exposed.Customer
import com.api.v2.customers.domain.CustomerCrudRepository
import com.api.v2.customers.exceptions.CustomerNotFoundException
import com.api.v2.customers.utils.exposed.CustomerFinder
import org.springframework.stereotype.Component

@Component
class CustomerFinderImpl(private val repository: CustomerCrudRepository): CustomerFinder {

    override fun findById(id: String): Customer {
        val foundCustomer = repository.findById(id)
        if (foundCustomer.isEmpty) {
            throw CustomerNotFoundException(id)
        }
        return foundCustomer.get()
    }
}