package com.api.v2.common

enum class ErrorMessages(val value: String) {

    DUPLICATED_SIN("Provided SIN is already in use."),
    DUPLICATED_EMAIL("Provided email is already in use.")

}