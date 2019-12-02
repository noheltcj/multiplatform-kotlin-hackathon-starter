package com.noheltcj.starter.adapter.entity

import com.noheltcj.starter.serialization.Serialized

data class AuthorizationResponse(
    @Serialized("access_token", []) val accessToken: String,
    @Serialized("expires_in", []) val expiresIn: Long,
    @Serialized("token_type", []) val tokenType: String,
    @Serialized("refresh_token", []) val refreshToken: String
)