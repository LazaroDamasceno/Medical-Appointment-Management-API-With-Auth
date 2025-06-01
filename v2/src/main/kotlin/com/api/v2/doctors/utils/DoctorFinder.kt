package com.api.v2.doctors.utils

import com.api.v2.doctors.domain.Doctor

interface DoctorFinder {
    fun findByLicenseNumber(licenseNumber: String): Doctor
}