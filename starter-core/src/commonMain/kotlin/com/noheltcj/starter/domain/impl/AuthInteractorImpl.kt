package com.noheltcj.starter.domain.impl

import com.noheltcj.starter.adapter.ApiAdapter
import com.noheltcj.starter.adapter.ClockAdapter
import com.noheltcj.starter.adapter.StorageAdapter
import com.noheltcj.starter.di.Inject
import com.noheltcj.starter.domain.AuthInteractor
import com.noheltcj.starter.entity.Authorization
import com.noheltcj.starter.extension.Completable
import com.noheltcj.rxcommon.Source
import com.noheltcj.rxcommon.observables.Single
import com.noheltcj.starter.extension.toCompletable

class AuthInteractorImpl @Inject constructor(
    private val apiAdapter: ApiAdapter,
    private val storageAdapter: StorageAdapter,
    private val clockAdapter: ClockAdapter
) : AuthInteractor {
    override fun refreshAuthorizationIfNecessary(): Completable {
        return storageAdapter.observeAuthorization()
            .flatMap { refreshAuthorizationIfExpirationIsNear(it) }
            .toCompletable()
    }

    override fun observeRefreshedAuthorization(): Source<Authorization?> {
        return storageAdapter.observeAuthorization()
            .flatMap { refreshAuthorizationIfExpirationIsNear(it) }
    }

    override fun signIn(email: String, password: String): Completable {
        return apiAdapter.exchangeCredentialsForAuthorization(email, password)
            .doOnNext { authorization ->
                storageAdapter.saveAuthorization(authorization)
            }
            .toCompletable()
    }

    override fun signUp(email: String, password: String): Completable {
        return apiAdapter.signUp(email, password)
            .doOnNext { storageAdapter.saveUser(it) }
            .flatMap { apiAdapter.exchangeCredentialsForAuthorization(email, password) }
            .doOnNext { storageAdapter.saveAuthorization(it) }
            .toCompletable()
    }

    override fun logout() {
        storageAdapter.saveAuthorization(null)
        storageAdapter.saveUser(null)
    }

    private fun refreshAuthorizationIfExpirationIsNear(authorization: Authorization?) : Single<Authorization?> {
        if (authorization == null) {
            return Single(just = null)
        } else if (clockAdapter.currentTimeEpochMilli > authorization.refreshTokenExpiresAtEpochMilli - ONE_MINUTE) {
            logout()
            return Single(just = null)
        }
        return if (clockAdapter.currentTimeEpochMilli > authorization.accessTokenExpiresAtEpochMilli - ONE_HOUR) {
            apiAdapter.refreshAuthorization(authorization)
                .map { it as Authorization? }
                .onErrorReturn {
                    return@onErrorReturn when (it) {
                        is ApiAdapter.UnexpectedResponseException -> {
                            when(it.statusCode) {
                                401 -> {
                                    logout()
                                }
                            }
                            Single<Authorization?>(null)
                        }
                        else -> {
                            Single(authorization)
                        }
                    }
                }
                .toSingle()
        } else {
            Single(authorization)
        }
    }

    companion object {
        const val ONE_MINUTE = 1000 * 60
        const val ONE_HOUR = ONE_MINUTE * 60
    }
}