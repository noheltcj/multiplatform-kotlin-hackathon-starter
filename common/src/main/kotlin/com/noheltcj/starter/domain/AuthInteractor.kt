package com.noheltcj.starter.domain

import com.noheltcj.starter.entity.Authorization
import com.noheltcj.rxcommon.Source
import com.noheltcj.starter.extension.Completable

interface AuthInteractor {
    fun refreshAuthorizationIfNecessary(): Completable
    fun observeRefreshedAuthorization(): Source<Authorization?>

    fun signIn(email: String, password: String): Completable
    fun signUp(email: String, password: String): Completable

    fun logout()

    class NotAuthorizedException : RuntimeException()
}