package com.noheltcj.starter.adapter.mapper

import com.noheltcj.starter.adapter.entity.UserResponse
import com.noheltcj.starter.di.Inject
import com.noheltcj.starter.entity.User

class UserMapper @Inject constructor() {
    fun mapUserResponse(userResponse: UserResponse): User {
        return User(
            id = userResponse.id,
            email = userResponse.email
        )
    }
}