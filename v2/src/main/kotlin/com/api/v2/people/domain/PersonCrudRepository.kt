package com.api.v2.people.domain

import com.api.v2.people.Person
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.MongoRepository

interface PersonCrudRepository: MongoRepository<Person, String> {

    @Query("{ '\$or': [ { 'sin': ?0, 'email': ?1 } ] }")
    fun findBySINOrEmail(sin: String, email: String): Person?
}