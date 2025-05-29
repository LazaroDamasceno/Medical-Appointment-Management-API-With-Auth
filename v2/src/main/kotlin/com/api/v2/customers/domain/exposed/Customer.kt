package com.api.v2.customers.domain.exposed

import com.api.v2.people.domain.exposed.Person
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document(collection = "People")
class Customer private constructor(
    var person: Person
) {

    @Id
    @Indexed(unique = true)
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
}