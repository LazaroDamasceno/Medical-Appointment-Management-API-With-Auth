package com.api.v2.people

import com.api.v2.common.formatFullName
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Document
class Person(
    var firstName: String,
    var middleName: String?,
    var lastName: String,
    val sin: String,
    var birthDate: LocalDate,
    var gender: Gender,
    var address: Address,
    var email: String,
    var phoneNumber: String
) {

    @Id
    val id: String = UUID.randomUUID().toString();
    val createdAt: LocalDateTime = LocalDateTime.now();
    var updatedAt: LocalDateTime? = null

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