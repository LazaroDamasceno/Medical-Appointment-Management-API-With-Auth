package com.api.v2.customers.domain

import com.api.v2.customers.domain.Customer
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CustomerRepository: CoroutineCrudRepository<Customer, String> {

    @Query("{ 'person.sin': ?0 }")
    fun findBySin(sin: String)

    @Query("{ 'person.email': ?0 }")
    fun findByEmail(email: String)
}