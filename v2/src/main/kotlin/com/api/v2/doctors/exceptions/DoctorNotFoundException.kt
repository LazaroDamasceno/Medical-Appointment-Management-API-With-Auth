package com.api.v2.doctors.exceptions

class DoctorNotFoundException(licenseNumber: String)
    : RuntimeException("Doctor whose license number is $licenseNumber was not found.")