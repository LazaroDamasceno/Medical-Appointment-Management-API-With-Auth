package com.api.v2.common

class DuplicatedBookingDateTimeException
    : RuntimeException("Provided booking date and time is already in use.")