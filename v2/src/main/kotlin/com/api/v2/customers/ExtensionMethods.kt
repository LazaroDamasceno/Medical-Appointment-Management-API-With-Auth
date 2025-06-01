package com.api.v2.customers

import com.api.v2.customers.Customer
import com.api.v2.customers.CustomerResponseDTO
import com.api.v2.people.fullName

fun Customer.toDTO(): CustomerResponseDTO {
    return CustomerResponseDTO(
        this.id,
        this.person.fullName()
    )
}