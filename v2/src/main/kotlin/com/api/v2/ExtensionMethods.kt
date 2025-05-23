package com.api.v2

import com.api.v2.customers.domain.Customer
import com.api.v2.customers.responses.CustomerResponseDTO
import com.api.v2.people.domain.exposed.Person

fun Person.fullName(): String {
    if (this.middleName == null) {
        return "${this.firstName} ${this.lastName}"
    }
    return "${this.firstName} ${this.middleName} ${this.lastName}"
}

fun Customer.toDTO(): CustomerResponseDTO {
    return CustomerResponseDTO.from(this)
}