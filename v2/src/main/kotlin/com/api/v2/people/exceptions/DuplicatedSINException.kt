package com.api.v2.people.exceptions

import java.lang.RuntimeException

class DuplicatedSINException: RuntimeException("Provided SIN is currently in use.")