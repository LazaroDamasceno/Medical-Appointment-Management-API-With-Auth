package com.api.v2.customers.responses

import com.api.v2.customers.domain.exposed.Customer
import com.api.v2.people.utils.fullName
import org.springframework.hateoas.RepresentationModel

class CustomerResponseDTO private constructor(
    val id: String,
    val fullName: String
): RepresentationModel<CustomerResponseDTO>() {

    companion object {
        fun from(customer: Customer): CustomerResponseDTO {
            return CustomerResponseDTO(
                customer.id,
                customer.person.fullName()
            )
        }
    }
}