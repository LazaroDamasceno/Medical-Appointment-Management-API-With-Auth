package com.api.v2.customers

import com.api.v2.people.domain.Person
import org.springframework.data.annotation.Id
import java.time.LocalDateTime
import java.util.UUID

class Customer private constructor(
    var person: Person
) {

    @Id
    val id: String = UUID.randomUUID().toString();
    val createdAt: LocalDateTime = LocalDateTime.now();
    var updatedAt: LocalDateTime? = null

    fun update(person: Person) {
        this.person = person
        this.updatedAt = LocalDateTime.now()
    }
}