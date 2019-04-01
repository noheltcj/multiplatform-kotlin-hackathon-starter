package com.noheltcj.starter.adapter

import com.noheltcj.starter.entity.*
import com.noheltcj.rxcommon.observables.Single
import com.noheltcj.starter.entity.Authorization

interface ApiAdapter {
    fun signUp(email: String, password: String): Single<User>
    fun exchangeCredentialsForAuthorization(email: String, password: String) : Single<Authorization>
    fun refreshAuthorization(authorization: Authorization) : Single<Authorization>
    fun initiatePasswordReset(email: String): Single<Unit>

    class UnknownException : RuntimeException()
    class UnexpectedResponseException(val statusCode: Int) : RuntimeException()
}