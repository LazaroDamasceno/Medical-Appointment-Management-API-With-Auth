package com.api.v2.customers.domain

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface CustomerRepository: MongoRepository<Customer, String> {

    @Query("{ 'person.sin': ?0 }")
    suspend fun findBySIN(sin: String)

    @Query("{ 'person.email': ?0 }")
    suspend fun findByEmail(email: String)
}