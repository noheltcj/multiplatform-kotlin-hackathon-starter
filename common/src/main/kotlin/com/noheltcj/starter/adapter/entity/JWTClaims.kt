package com.noheltcj.starter.adapter.entity

import com.noheltcj.starter.serialization.Serialized

data class JWTClaims(
    @Serialized("sub", []) val subject: String,
    @Serialized("exp", []) val expiresAtEpochSecond: Long,
    @Serialized("iat", []) val issuedAtEpochSecond: Long
)