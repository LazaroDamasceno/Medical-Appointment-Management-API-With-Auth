package com.api.v2.people.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document
data class PersonAuditTrail(
    @Id
    val id: String,
    val person: Person,
    val createdAt: LocalDateTime
) {

    companion object {
        fun of( person: Person): PersonAuditTrail {
            return PersonAuditTrail(
                UUID.randomUUID().toString(),
                person,
                LocalDateTime.now()
            )
        }
    }
}
