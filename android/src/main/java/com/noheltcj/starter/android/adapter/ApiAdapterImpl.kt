package com.noheltcj.starter.android.adapter

import android.content.Context
import android.util.Base64
import com.google.gson.Gson
import com.noheltcj.rxcommon.observables.Single
import com.noheltcj.starter.android.R
import com.noheltcj.starter.adapter.ApiAdapter
import com.noheltcj.starter.adapter.entity.JWTClaims
import com.noheltcj.starter.adapter.mapper.AuthorizationMapper
import com.noheltcj.starter.adapter.mapper.UserMapper
import com.noheltcj.starter.android.adapter.mapper.HttpExceptionMapper
import com.noheltcj.starter.android.adapter.repository.ApiRepository
import com.noheltcj.starter.android.extension.toRxCommon
import com.noheltcj.starter.entity.Authorization
import com.noheltcj.starter.entity.User
import io.reactivex.Scheduler
import java.nio.charset.Charset
import javax.inject.Inject
import javax.inject.Named

class ApiAdapterImpl @Inject constructor(
    private val apiRepository: ApiRepository,
    private val authorizationMapper: AuthorizationMapper,
    private val userMapper: UserMapper,
    private val httpExceptionMapper: HttpExceptionMapper,
    @Named("main") private val scheduler: Scheduler,
    private val context: Context
) : ApiAdapter {
    private val gson = Gson()
    private val clientId by lazy { context.getString(R.string.client_id) }

    override fun signUp(email: String, password: String): Single<User> {
        return apiRepository.signUpNewUser(email, password, clientId)
            .map { userMapper.mapUserResponse(it) }
            .onErrorResumeNext { io.reactivex.Single.error(httpExceptionMapper.mapToExpectedExceptionIfPossible(it)) }
            .observeOn(scheduler)
            .toRxCommon()
    }

    override fun exchangeCredentialsForAuthorization(email: String, password: String): Single<Authorization> {
        return apiRepository
            .exchangeCredentialsForAuthorization(email, password, clientId, "password")
            .map {
                val decodedAccessToken = decodeJWTClaims(it.accessToken)
                val decodedRefreshToken = decodeJWTClaims(it.refreshToken)
                authorizationMapper.mapAuthorizationResponse(
                    authorizationResponse = it,
                    accessTokenClaims = decodedAccessToken,
                    refreshTokenClaims = decodedRefreshToken
                )
            }
            .onErrorResumeNext { io.reactivex.Single.error(httpExceptionMapper.mapToExpectedExceptionIfPossible(it)) }
            .observeOn(scheduler)
            .toRxCommon()
    }

    override fun refreshAuthorization(authorization: Authorization): Single<Authorization> {
        return apiRepository
            .refreshAuthorization(authorization.refreshToken, clientId, "refresh_token")
            .map {
                val decodedAccessToken = decodeJWTClaims(it.accessToken)
                val decodedRefreshToken = decodeJWTClaims(it.refreshToken)
                authorizationMapper.mapAuthorizationResponse(
                    authorizationResponse = it,
                    accessTokenClaims = decodedAccessToken,
                    refreshTokenClaims = decodedRefreshToken
                )
            }
            .onErrorResumeNext { io.reactivex.Single.error(httpExceptionMapper.mapToExpectedExceptionIfPossible(it)) }
            .observeOn(scheduler)
            .toRxCommon()
    }

    override fun initiatePasswordReset(email: String): Single<Unit> {
        return apiRepository.submitForgotPasswordEmail(email, clientId)
            .observeOn(scheduler)
            .toRxCommon()
    }

    // Consider using auth0's JWT decoder
    private fun decodeJWTClaims(token: String) : JWTClaims {
        val claimsPart = token.substring(token.indexOf('.') + 1, token.lastIndexOf('.'))
        val json = Base64.decode(claimsPart, Base64.DEFAULT)!!.toString(Charset.defaultCharset())
        return gson.fromJson(json, JWTClaims::class.java)
    }
}