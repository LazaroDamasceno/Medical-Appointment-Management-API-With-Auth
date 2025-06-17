package com.api.v2.customers

import com.api.v2.people.Person
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document(collection = "People")
data class Customer(
    @Id
    val id: String,
    val person: Person,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
) {

    companion object {
        fun update(person: Person): Customer {
            return Customer(
                UUID.randomUUID().toString(),
                person,
                LocalDateTime.now(),
                null
            )
        }
    }

    fun update(person: Person): Customer {
        return Customer(
            this.id,
            person,
            this.createdAt,
            LocalDateTime.now()
        )
    }
}