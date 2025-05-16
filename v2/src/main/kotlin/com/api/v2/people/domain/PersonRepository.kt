package com.api.v2.people.domain

import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PersonRepository: CoroutineCrudRepository<Person, String> {

    @Query("{ '\$or': [ { 'sin': ?0, 'email': ?1 } ] }")
    suspend fun findBySINOrEmail(sin: String, email: String): Person?
}