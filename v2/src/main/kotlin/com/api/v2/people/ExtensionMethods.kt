package com.api.v2.people

fun Person.fullName(): String {
    if (this.middleName == null) {
        return "${this.firstName} ${this.lastName}"
    }
    return "${this.firstName} ${this.middleName} ${this.lastName}"
}