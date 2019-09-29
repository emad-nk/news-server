package com.upday.exception

import java.lang.RuntimeException

class ConstraintsViolationException(override val message: String) : RuntimeException(message)
