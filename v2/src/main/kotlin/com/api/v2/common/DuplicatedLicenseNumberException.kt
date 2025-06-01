package com.api.v2.common

class DuplicatedLicenseNumberException:
        RuntimeException("Provided license number is already in use.")