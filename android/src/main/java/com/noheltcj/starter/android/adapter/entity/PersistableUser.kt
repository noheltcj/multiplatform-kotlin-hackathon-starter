package com.noheltcj.starter.android.adapter.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.noheltcj.starter.entity.User

/**
 * Not a typical use of a database, but this starter isn't complex enough to warrant a database.
 */
@Entity
data class PersistableUser(
    @PrimaryKey val id: String = "ONLY_ONE",
    val remoteId: String,
    val email: String
) {
    constructor(user: User): this(
        remoteId = user.id,
        email = user.email
    )

    fun toInternal(): User {
        return User(
            id = remoteId,
            email = email
        )
    }
}