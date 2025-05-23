package com.api.v2.customers.services

import com.api.v2.customers.responses.CustomerResponseDTO
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity

interface CustomerRetrievalService {
    suspend fun findById(id: String): ResponseEntity<CustomerResponseDTO>
    suspend fun findAll(pageable: Pageable): ResponseEntity<List<CustomerResponseDTO>>
}