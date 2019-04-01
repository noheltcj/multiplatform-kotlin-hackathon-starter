package com.noheltcj.starter.android.adapter.room.dao

import androidx.room.*
import com.noheltcj.starter.android.adapter.entity.PersistableUser
import io.reactivex.Flowable

/**
 * As weird as this one is, it's only intended to hold one user.
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM PersistableUser")
    fun observeUsers(): Flowable<List<PersistableUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertUser(user: PersistableUser): Long

    @Query("DELETE FROM PersistableUser")
    fun deleteUser()
}