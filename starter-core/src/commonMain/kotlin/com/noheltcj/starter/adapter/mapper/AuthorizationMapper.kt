package com.noheltcj.starter.adapter.mapper

import com.noheltcj.starter.adapter.entity.AuthorizationResponse
import com.noheltcj.starter.adapter.entity.JWTClaims
import com.noheltcj.starter.di.Inject
import com.noheltcj.starter.entity.Authorization

class AuthorizationMapper @Inject constructor() {
    fun mapAuthorizationResponse(
        authorizationResponse: AuthorizationResponse,
        accessTokenClaims: JWTClaims,
        refreshTokenClaims: JWTClaims
    ): Authorization {
        return Authorization(
            accessToken = authorizationResponse.accessToken,
            accessTokenExpiresAtEpochMilli = accessTokenClaims.expiresAtEpochSecond * 1000,
            accessTokenIssuedAtEpochMilli = accessTokenClaims.issuedAtEpochSecond * 1000,
            tokenType = authorizationResponse.tokenType,
            refreshToken = authorizationResponse.refreshToken,
            refreshTokenExpiresAtEpochMilli = refreshTokenClaims.expiresAtEpochSecond * 1000
        )
    }
}