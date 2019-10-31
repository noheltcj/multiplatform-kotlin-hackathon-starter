package com.noheltcj.starter.entity

data class Authorization(
    val accessToken: String,
    val accessTokenExpiresAtEpochMilli: Long,
    val accessTokenIssuedAtEpochMilli: Long,
    val tokenType: String,
    val refreshToken: String,
    val refreshTokenExpiresAtEpochMilli: Long
)