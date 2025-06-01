package com.api.v2.doctors.services

import com.api.v2.doctors.controller.DoctorController
import com.api.v2.doctors.domain.DoctorCrudRepository
import com.api.v2.doctors.domain.Doctor
import com.api.v2.doctors.responses.DoctorResponseDTO
import com.api.v2.doctors.utils.DoctorFinder
import com.api.v2.doctors.utils.toDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class DoctorRetrievalServiceImpl(
    private val crudRepository: DoctorCrudRepository,
    private val doctorFinder: DoctorFinder
) : DoctorRetrievalService {

    override fun findByLicenseNumber(licenseNumber: String): ResponseEntity<DoctorResponseDTO> {
        val foundDoctor = doctorFinder.findByLicenseNumber(licenseNumber)
        val dto = foundDoctor.toDTO()
        dto.add(
            linkTo(methodOn(DoctorController::class.java).findByLicenseNumber(licenseNumber))
                .withSelfRel()
        )
        return ResponseEntity.ok(dto)
    }

    override fun findAll(pageable: Pageable): ResponseEntity<Page<DoctorResponseDTO>> {
        val all = crudRepository
            .findAll(pageable)
            .map(Doctor::toDTO)
        return ResponseEntity.ok(all)
    }
}