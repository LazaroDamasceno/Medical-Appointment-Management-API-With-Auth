package com.api.v2.people.domain

import com.api.v2.people.dtos.Address
import com.api.v2.people.enums.Gender
import com.api.v2.people.requests.PersonRegistrationDTO
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Document
class Person private constructor(
    var firstName: String,
    var middleName: String?,
    var lastName: String,
    var birthDate: LocalDate,
    var sin: String,
    var email: String,
    var phoneNumber: String,
    var gender: Gender,
    var address: Address
){

    var id = UUID.randomUUID().toString();
    val createdAt: LocalDateTime = LocalDateTime.now()
    val updatedAt: LocalDateTime? = null

    companion object {
        fun of(registrationDto: PersonRegistrationDTO): Person {
            return Person(
                registrationDto.firstName,
                registrationDto.middleName,
                registrationDto.lastName,
                registrationDto.birthDate,
                registrationDto.sin,
                registrationDto.email,
                registrationDto.phoneNumber,
                registrationDto.gender,
                registrationDto.address
            )
        }
    }
}