package com.api.v2.people

import com.api.v2.people.dtos.Address
import com.api.v2.people.enums.Gender
import com.api.v2.people.requests.PersonRegistrationDTO
import com.api.v2.people.requests.PersonUpdateDTO
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Document(collection = "People")
class Person private constructor(
    var firstName: String,
    var middleName: String?,
    var lastName: String,
    var birthDate: LocalDate,
    @Indexed(unique = true)
    var sin: String,
    @Indexed(unique = true)
    var email: String,
    var phoneNumber: String,
    var gender: Gender,
    var address: Address
){

    @Id
    var id = UUID.randomUUID().toString();
    val createdAt: LocalDateTime = LocalDateTime.now()
    var updatedAt: LocalDateTime? = null

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

    fun update(updatingDTO: PersonUpdateDTO) {
        firstName = updatingDTO.firstName
        middleName = updatingDTO.middleName
        lastName = updatingDTO.lastName
        birthDate = updatingDTO.birthDate
        email = updatingDTO.email
        phoneNumber = updatingDTO.phoneNumber
        gender = updatingDTO.gender
        address = updatingDTO.address
        updatedAt = LocalDateTime.now()
    }
}