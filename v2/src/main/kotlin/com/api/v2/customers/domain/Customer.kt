package com.api.v2.customers.domain

import com.api.v2.people.domain.Person
import org.springframework.data.annotation.Id
import java.time.LocalDateTime
import java.util.UUID

class Customer private constructor(
    var person: Person
) {

    @Id
    var id: String = UUID.randomUUID().toString()
    val createdAt: LocalDateTime = LocalDateTime.now()
    var updatedAt: LocalDateTime? = null

    companion object {
        fun of(person: Person): Customer {
            return Customer(person)
        }
    }

    fun update(person: Person) {
        this.person = person
        this.updatedAt = LocalDateTime.now()
    }
}