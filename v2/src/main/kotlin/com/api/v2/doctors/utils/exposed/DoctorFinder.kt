package com.api.v2.doctors.utils.exposed

import com.api.v2.doctors.domain.exposed.Doctor

interface DoctorFinder {
    fun findByLicenseNumber(licenseNumber: String): Doctor
}