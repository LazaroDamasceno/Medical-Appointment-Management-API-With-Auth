package com.api.v2.customers.domain

import com.api.v2.customers.responses.CustomerResponseDTO
import com.api.v2.people.domain.exposed.Person
import com.api.v2.people.extension_methods.fullName
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document
class Customer private constructor(
    var person: Person
) {

    var id = UUID.randomUUID().toString();
    val createdAt: LocalDateTime = LocalDateTime.now()
    var updatedAt: LocalDateTime? = null

    companion object {
        fun update(person: Person): Customer {
            return Customer(person)
        }
    }

    fun update(person: Person) {
        this.person = person
        this.updatedAt = LocalDateTime.now()
    }

    fun toDTO(): CustomerResponseDTO {
        return CustomerResponseDTO(
            id,
            person.fullName()
        )
    }
}