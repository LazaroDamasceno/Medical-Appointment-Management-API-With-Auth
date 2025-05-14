package com.api.v2.common

class ResultData<T> private constructor(
    val status: ResultStatus,
    val message: String,
    val body: T?
) {

    companion object {
        fun <T> success(body: T): ResultData<T> {
            return ResultData(
                ResultStatus.SUCCESSFUL,
                "Successful operation.",
                body
            )
        }

        fun <T> created(body: T): ResultData<T> {
            return ResultData(
                ResultStatus.SUCCESSFUL,
                "Resource created.",
                body
            )
        }

        fun <T> error(message: String): ResultData<T> {
            return ResultData(
                ResultStatus.ERROR,
                message,
                null
            )
        }
    }
}