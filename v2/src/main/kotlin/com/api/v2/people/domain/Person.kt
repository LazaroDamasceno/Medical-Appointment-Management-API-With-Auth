package com.api.v2.people.domain

import com.api.v2.people.dtos.Address
import com.api.v2.people.enums.Gender
import com.api.v2.people.requests.PersonRegistrationDto
import com.api.v2.people.requests.PersonUpdatingDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Document
class Person private constructor(
    private var firstName: String,
    private var middleName: String?,
    private var lastName: String,
    private val sin: String,
    private var birthDate: LocalDate,
    private var gender: Gender,
    private var address: Address,
    private var email: String,
    private var phoneNumber: String
) {

    @Id
    private var id: String = UUID.randomUUID().toString()
    private val createdAt: LocalDateTime = LocalDateTime.now()
    private var updatedAt: LocalDateTime? = null

    companion object {
        fun of(registrationDto: PersonRegistrationDto): Person {
            return Person(
                registrationDto.firstName,
                registrationDto.middleName,
                registrationDto.lastName,
                registrationDto.sin,
                registrationDto.birthDate,
                registrationDto.gender,
                registrationDto.address,
                registrationDto.email,
                registrationDto.phoneNumber
            )
        }
    }

    fun update(updatingDto: PersonUpdatingDto) {
        firstName = updatingDto.firstName
        middleName = updatingDto.middleName
        lastName = updatingDto.lastName
        birthDate = updatingDto.birthDate
        gender = updatingDto.gender
        address = updatingDto.address
        email = updatingDto.email
        phoneNumber = updatingDto.phoneNumber
        updatedAt = LocalDateTime.now()
    }


}