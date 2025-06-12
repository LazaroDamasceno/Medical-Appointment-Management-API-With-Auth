package com.api.v2.people

import com.api.v2.people.dtos.Address
import com.api.v2.people.enums.Gender
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Document(collection = "People")
data class Person(
    @Id
    val id: String,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val birthDate: LocalDate,
    @Indexed(unique = true)
    val sin: String,
    @Indexed(unique = true)
    val email: String,
    val phoneNumber: String,
    val gender: Gender,
    val address: Address,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
) {

    companion object {
        fun of(registrationDto: PersonRegistrationDTO): Person {
            return Person(
                UUID.randomUUID().toString(),
                registrationDto.firstName,
                registrationDto.middleName,
                registrationDto.lastName,
                registrationDto.birthDate,
                registrationDto.sin,
                registrationDto.email,
                registrationDto.phoneNumber,
                registrationDto.gender,
                registrationDto.address,
                LocalDateTime.now(),
                null
            )
        }
    }

    fun update(updatingDTO: PersonUpdateDTO): Person {
        return Person(
            this.id,
            updatingDTO.firstName,
            updatingDTO.middleName,
            updatingDTO.lastName,
            updatingDTO.birthDate,
            this.sin,
            updatingDTO.email,
            updatingDTO.phoneNumber,
            updatingDTO.gender,
            updatingDTO.address,
            this.createdAt,
            LocalDateTime.now()
        )
    }
}