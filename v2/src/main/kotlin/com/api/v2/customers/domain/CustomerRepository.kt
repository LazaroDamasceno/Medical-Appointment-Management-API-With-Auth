package com.api.v2.customers.domain

import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.Optional

interface CustomerRepository: CoroutineCrudRepository<Customer, String> {

    @Query("{ 'person.sin': ?0 }")
    fun findBySin(sin: String): Optional<Customer>

    @Query("{ 'person.email': ?0 }")
    fun findByEmail(email: String): Optional<Customer>
}