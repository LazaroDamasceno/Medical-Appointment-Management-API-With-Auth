package com.api.v2.common

import com.api.v2.people.domain.Person

fun Person.fullName(person: Person): String {
    val firstName = person.firstName
    val middleName = person.middleName
    val lastName = person.lastName
    if (middleName == null) {
        return "$firstName $lastName"
    }
    return "$firstName $middleName $lastName"
}