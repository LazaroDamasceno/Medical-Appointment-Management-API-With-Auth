package com.api.v2.customers.utils.exposed

import com.api.v2.customers.Customer
import com.api.v2.customers.responses.CustomerResponseDTO
import com.api.v2.people.fullName

fun Customer.toDTO(): CustomerResponseDTO {
    return CustomerResponseDTO(
        this.id,
        this.person.fullName()
    )
}