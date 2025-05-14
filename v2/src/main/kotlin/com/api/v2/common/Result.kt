package com.api.v2.common

class Result<T> private constructor(
    val status: ResultStatus,
    val message: String,
    val body: T?
) {

    companion object {
        fun <T> success(body: T): Result<T> {
            return Result(
                ResultStatus.SUCCESSFUL,
                "Successful operation.",
                body
            )
        }

        fun <T> created(body: T): Result<T> {
            return Result(
                ResultStatus.SUCCESSFUL,
                "Resource created.",
                body
            )
        }

        fun <T> error(message: String): Result<T> {
            return Result(
                ResultStatus.ERROR,
                message,
                null
            )
        }
    }
}