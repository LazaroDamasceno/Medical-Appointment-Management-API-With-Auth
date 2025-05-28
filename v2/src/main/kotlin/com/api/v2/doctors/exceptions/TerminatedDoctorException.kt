package com.api.v2.doctors.exceptions

import java.lang.RuntimeException

class TerminatedDoctorException
    : RuntimeException("Sought doctor is currently terminated.")