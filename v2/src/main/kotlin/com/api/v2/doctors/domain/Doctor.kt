package com.api.v2.doctors.domain

import com.api.v2.common.ProfessionalStatus
import com.api.v2.people.domain.Person
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document(collection = "Doctors")
class Doctor private constructor(
    @Indexed(unique = true)
    var licenseNumber: String,
    var person: Person
) {

    @Id
    var id: String = UUID.randomUUID().toString()
    var status: ProfessionalStatus = ProfessionalStatus.ACTIVE
    val createdAt: LocalDateTime = LocalDateTime.now()
    var updatedAt: LocalDateTime? = null
    var terminatedAt: LocalDateTime? = null

    companion object {
        fun of(licenseNumber: String, person: Person): Doctor {
            return Doctor(licenseNumber, person)
        }
    }

    fun markAsTerminated() {
        status = ProfessionalStatus.TERMINATED
        terminatedAt = LocalDateTime.now()
    }

    fun markAsRehired() {
        status = ProfessionalStatus.ACTIVE
        terminatedAt = null
    }

    fun update(person: Person) {
        this.person = person
        this.updatedAt = LocalDateTime.now()
    }


}