package com.noheltcj.starter.android.adapter.repository

import com.noheltcj.starter.adapter.entity.AuthorizationResponse
import com.noheltcj.starter.adapter.entity.UserResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiRepository {
    @POST("/auth/token")
    @FormUrlEncoded
    fun exchangeCredentialsForAuthorization(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("client_id") clientId: String,
        @Field("grant_type") grantType: String
    ): Single<AuthorizationResponse>

    @POST("/auth/token")
    @FormUrlEncoded
    fun refreshAuthorization(
        @Field("refresh_token") refreshToken: String,
        @Field("client_id") clientId: String,
        @Field("grant_type") grantType: String
    ): Single<AuthorizationResponse>

    @POST("/auth/signup")
    @FormUrlEncoded
    fun signUpNewUser(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("client_id") clientId: String
    ): Single<UserResponse>

    @POST("/auth/reset")
    @FormUrlEncoded
    fun submitForgotPasswordEmail(
        @Field("email") email: String,
        @Field("client_id") clientId: String
    ): Completable
}