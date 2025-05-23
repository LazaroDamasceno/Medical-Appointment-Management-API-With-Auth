package com.api.v2.customers.domain

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface CustomerCrudRepository: MongoRepository<Customer, String> {

    @Query("{ 'person.sin': ?0 }")
    suspend fun findBySIN(sin: String): Customer?

    @Query("{ 'person.email': ?0 }")
    suspend fun findByEmail(email: String): Customer?
}