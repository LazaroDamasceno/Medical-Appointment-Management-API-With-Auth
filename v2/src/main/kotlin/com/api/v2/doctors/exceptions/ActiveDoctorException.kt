package com.api.v2.doctors.exceptions

import java.lang.RuntimeException

class ActiveDoctorException
    : RuntimeException("Sought doctor is currently active.")