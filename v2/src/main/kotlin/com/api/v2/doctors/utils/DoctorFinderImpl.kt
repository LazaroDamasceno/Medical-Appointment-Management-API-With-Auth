package com.api.v2.doctors.utils

import com.api.v2.doctors.domain.DoctorCrudRepository
import com.api.v2.doctors.domain.exposed.Doctor
import com.api.v2.doctors.exceptions.DoctorNotFoundException
import com.api.v2.doctors.utils.exposed.DoctorFinder
import org.springframework.data.mongodb.core.index.CompoundIndex

@CompoundIndex
class DoctorFinderImpl: DoctorFinder {

    private val crudRepository: DoctorCrudRepository

    constructor(crudRepository: DoctorCrudRepository) {
        this.crudRepository = crudRepository
    }

    override fun findByLicenseNumber(licenseNumber: String): Doctor {
        val foundDoctor = crudRepository.findByLicenseNumber(licenseNumber)
        if (foundDoctor == null) {
            throw DoctorNotFoundException(licenseNumber)
        }
        return foundDoctor
    }
}