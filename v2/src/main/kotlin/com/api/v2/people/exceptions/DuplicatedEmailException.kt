package com.api.v2.people.exceptions

import java.lang.RuntimeException

class DuplicatedEmailException: RuntimeException("Provided email is currently in use.")