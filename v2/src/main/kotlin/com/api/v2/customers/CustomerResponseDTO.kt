package com.api.v2.customers

import org.springframework.hateoas.RepresentationModel

class CustomerResponseDTO(
    val id: String,
    val fullName: String
): RepresentationModel<CustomerResponseDTO>()