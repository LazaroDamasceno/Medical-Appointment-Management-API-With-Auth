package com.api.v2.customers.domain

import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CustomerRepository: CoroutineCrudRepository<Customer, String> {

    @Query("{ 'person.sin': ?0 }")
    suspend fun findBySin(sin: String): Customer?

    @Query("{ 'person.email': ?0 }")
    suspend fun findByEmail(email: String): Customer?
}