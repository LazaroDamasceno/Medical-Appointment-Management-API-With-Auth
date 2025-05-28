package com.api.v2.doctors.services

import com.api.v2.doctors.domain.DoctorCrudRepository
import com.api.v2.doctors.domain.exposed.Doctor
import com.api.v2.doctors.responses.DoctorResponseDTO
import com.api.v2.doctors.utils.exposed.DoctorFinder
import com.api.v2.doctors.utils.exposed.toDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class DoctorRetrievalServiceImpl: DoctorRetrievalService {

    private val crudRepository: DoctorCrudRepository
    private val doctorFinder: DoctorFinder

    constructor(crudRepository: DoctorCrudRepository, doctorFinder: DoctorFinder) {
        this.crudRepository = crudRepository
        this.doctorFinder = doctorFinder
    }

    override fun findByLicenseNumber(licenseNumber: String): ResponseEntity<DoctorResponseDTO> {
        val foundDoctor = doctorFinder.findByLicenseNumber(licenseNumber)
        val dto = foundDoctor.toDTO()
        return ResponseEntity.ok(dto)
    }

    override fun findAll(pageable: Pageable): ResponseEntity<Page<DoctorResponseDTO>> {
        val all = crudRepository
            .findAll(pageable)
            .map(Doctor::toDTO)
        return ResponseEntity.ok(all)
    }
}