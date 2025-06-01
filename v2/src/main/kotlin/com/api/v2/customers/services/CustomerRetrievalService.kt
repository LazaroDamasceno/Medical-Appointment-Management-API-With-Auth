package com.api.v2.customers.services

import com.api.v2.customers.responses.CustomerResponseDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity

interface CustomerRetrievalService {
    fun findById(id: String): ResponseEntity<CustomerResponseDTO>
    fun findAll(pageable: Pageable): ResponseEntity<Page<CustomerResponseDTO>>
}