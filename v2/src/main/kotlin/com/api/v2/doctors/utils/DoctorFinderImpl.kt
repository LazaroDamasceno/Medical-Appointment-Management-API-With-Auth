package com.api.v2.doctors.utils

import com.api.v2.doctors.domain.DoctorCrudRepository
import com.api.v2.doctors.Doctor
import com.api.v2.doctors.exceptions.DoctorNotFoundException
import com.api.v2.doctors.DoctorFinder
import org.springframework.stereotype.Component

@Component
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