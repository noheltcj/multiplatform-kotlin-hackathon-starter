package com.noheltcj.starter.adapter.entity

import com.noheltcj.starter.serialization.Serialized

data class UserResponse(
    @Serialized("id", []) val id: String,
    @Serialized("email", []) val email: String,
    @Serialized("created_at", []) val createdAt: Long,
    @Serialized("updated_at", []) val updatedAt: Long
)