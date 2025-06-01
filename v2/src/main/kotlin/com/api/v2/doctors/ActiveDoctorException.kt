package com.api.v2.doctors

import java.lang.RuntimeException

class ActiveDoctorException
    : RuntimeException("Sought doctor is currently active.")