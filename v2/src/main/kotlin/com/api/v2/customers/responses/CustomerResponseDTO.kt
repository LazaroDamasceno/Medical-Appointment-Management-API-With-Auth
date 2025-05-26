package com.api.v2.customers.responses

import com.api.v2.customers.domain.exposed.Customer
import com.api.v2.people.utils.fullName

class CustomerResponseDTO private constructor(
    val id: String,
    val fullName: String
) {

    companion object {
        fun from(customer: Customer): CustomerResponseDTO {
            return CustomerResponseDTO(
                customer.id,
                customer.person.fullName()
            )
        }
    }
}