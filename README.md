# Multiplatform Kotlin Hackathon Starter
This project is a starting point for sharing core and presentation logic between iOS and Android.

## Project Structure
The top level project can be imported into Android Studio or IntelliJ as a gradle project.

I personally prefer IntelliJ since it has the lesser of two inconveniences. Sources generated from the databinding annotation processor are usually missed completely, but as long as you do the imports properly, it will still compile and run.

By default this project assumes that you have a server running locally on port 3000 conforming to the following interface. I did not provide code for this server, if you want to skip actually making network requests, simply fake the implementation of methods required by the ApiAdapter interface.
```kotlin
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
```

AuthorizationResponse:
```kotlin
data class AuthorizationResponse(
    @Serialized("access_token", []) val accessToken: String,
    @Serialized("expires_in", []) val expiresIn: Long,
    @Serialized("token_type", []) val tokenType: String,
    @Serialized("refresh_token", []) val refreshToken: String
)
```

UserResponse:
```kotlin
data class UserResponse(
    @Serialized("id", []) val id: String,
    @Serialized("email", []) val email: String,
    @Serialized("created_at", []) val createdAt: Long,
    @Serialized("updated_at", []) val updatedAt: Long
)
```

The access and refresh tokens should be a JWT and contain at least the following claims:
```kotlin
data class JWTClaims(
    @Serialized("sub", []) val subject: String,
    @Serialized("exp", []) val expiresAtEpochSecond: Long,
    @Serialized("iat", []) val issuedAtEpochSecond: Long
)
```

## Running Android
If you've imported the project correctly into intelliJ or Android Studio, you will be able to tweak implementations (removing the actual networking if necessary). Running the application should be simple once synchronization completes.

## Preparing and running iOS
The native portion of the project is compiled to a framework, which is then imported via cocoapods as a vendored framework. You can see examples of accessing the framework throughout the XCode project.

I've supplied a script to make the packaging process easier (it builds more than is necessary and if you want, just remove the pod linting done at the end).

From the base directory:
```bash
sh build.sh
```

Then navigate into the ios directory from the project root and run the following to generate the xcworkspace with the framework.
```bash
pod install
```

_Note: I sometimes have to remove Info.plist output in the target Build Phases after pod install_
