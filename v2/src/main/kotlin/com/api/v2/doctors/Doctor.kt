package com.api.v2.doctors

import com.api.v2.common.ProfessionalStatus
import com.api.v2.people.Person
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document(collection = "Doctors")
data class Doctor(
    @Id
    val id: String,
    @Indexed(unique = true)
    val licenseNumber: String,
    val person: Person,
    val status: ProfessionalStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val terminatedAt: LocalDateTime?
) {

    companion object {
        fun of(licenseNumber: String, person: Person): Doctor {
            return Doctor(
                UUID.randomUUID().toString(),
                licenseNumber,
                person,
                ProfessionalStatus.ACTIVE,
                LocalDateTime.now(),
                null,
                null
            )
        }
    }

    fun markAsTerminated(): Doctor {
        return Doctor(
            this.id,
            this.licenseNumber,
            this.person,
            ProfessionalStatus.TERMINATED,
            this.createdAt,
            this.updatedAt,
            LocalDateTime.now()
        )
    }

    fun markAsRehired(): Doctor {
        return Doctor(
            this.id,
            this.licenseNumber,
            this.person,
            ProfessionalStatus.ACTIVE,
            this.createdAt,
            this.updatedAt,
            null
        )
    }

    fun update(person: Person): Doctor {
        return Doctor(
            this.id,
            this.licenseNumber,
            person,
            this.status,
            this.createdAt,
            LocalDateTime.now(),
            this.updatedAt
        )
    }


}