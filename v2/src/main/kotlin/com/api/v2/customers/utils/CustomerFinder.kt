package com.api.v2.customers.utils

import com.api.v2.customers.domain.Customer
import com.api.v2.customers.domain.CustomerRepository
import org.springframework.stereotype.Component

@Component
class CustomerFinder(private val repository: CustomerRepository) {

    suspend fun findNullableById(id: String): Customer? {
        return repository.findById(id)
    }
}