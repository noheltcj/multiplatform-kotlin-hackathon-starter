package com.noheltcj.starter.android.adapter.mapper

import com.noheltcj.starter.adapter.ApiAdapter
import retrofit2.HttpException
import javax.inject.Inject

class HttpExceptionMapper @Inject constructor() {
    fun mapToExpectedExceptionIfPossible(throwable: Throwable): Throwable {
        return if (throwable is HttpException) {
            ApiAdapter.UnexpectedResponseException(throwable.code())
        } else {
            throwable.printStackTrace()
            throwable
        }
    }
}