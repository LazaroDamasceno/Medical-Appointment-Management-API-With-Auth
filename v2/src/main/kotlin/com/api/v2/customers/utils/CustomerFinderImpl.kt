package com.api.v2.customers.utils

import com.api.v2.customers.Customer
import com.api.v2.customers.domain.CustomerCrudRepository
import com.api.v2.customers.exceptions.CustomerNotFoundException
import com.api.v2.customers.CustomerFinder
import org.springframework.stereotype.Component

@Component
class CustomerFinderImpl(val repository: CustomerCrudRepository): CustomerFinder {

    override fun findById(id: String): Customer {
        val foundCustomer = repository.findById(id)
        if (foundCustomer.isEmpty) {
            throw CustomerNotFoundException(id)
        }
        return foundCustomer.get()
    }
}