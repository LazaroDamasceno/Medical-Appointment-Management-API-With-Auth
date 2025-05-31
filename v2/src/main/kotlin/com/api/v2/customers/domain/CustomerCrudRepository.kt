package com.api.v2.customers.domain

import com.api.v2.customers.Customer
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface CustomerCrudRepository: MongoRepository<Customer, String> {

    @Query("{ 'person.sin': ?0 }")
    fun findBySIN(sin: String): Customer?

    @Query("{ 'person.email': ?0 }")
    fun findByEmail(email: String): Customer?
}