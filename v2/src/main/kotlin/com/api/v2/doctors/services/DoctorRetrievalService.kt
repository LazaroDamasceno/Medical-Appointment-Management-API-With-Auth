package com.api.v2.doctors.services

import com.api.v2.doctors.responses.DoctorResponseDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity

interface DoctorRetrievalService {
    fun findByLicenseNumber(licenseNumber: String): ResponseEntity<DoctorResponseDTO>
    fun findAll(pageable: Pageable): ResponseEntity<Page<DoctorResponseDTO>>
}