package com.api.v2.people

import java.lang.RuntimeException

class DuplicatedSINException: RuntimeException("Provided SIN is currently in use.")