package com.api.v2.people.domain

import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PersonRepository: CoroutineCrudRepository<Person, String> {

    @Query("{ 'sin': ?0 }")
    suspend fun findBySIN(sin: String): Person?

    @Query("{ 'email': ?0 }")
    suspend fun findByEmail(email: String): Person?
}