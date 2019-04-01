package com.noheltcj.starter.android.adapter

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.noheltcj.rxcommon.Source
import com.noheltcj.rxcommon.subjects.BehaviorRelay
import com.noheltcj.starter.adapter.StorageAdapter
import com.noheltcj.starter.android.adapter.entity.PersistableUser
import com.noheltcj.starter.android.adapter.room.dao.UserDao
import com.noheltcj.starter.android.extension.toRxCommon
import com.noheltcj.starter.entity.Authorization
import com.noheltcj.starter.entity.User
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class StorageAdapterImpl @Inject constructor(
    context: Context,
    private val userDao: UserDao,
    @Named("main") private val scheduler: Scheduler
) : StorageAdapter {
    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
    private val gson = Gson()

    private val currentAuthorizationRelay by lazy { BehaviorRelay(getCurrentAuthorization()) }

    override fun observeUser(): Source<User?> {
        return userDao.observeUsers()
            .observeOn(scheduler)
            .toRxCommon()
            .map { it.firstOrNull()?.toInternal() }
    }

    override fun saveUser(user: User?) {
        if (user != null) {
            userDao.upsertUser(PersistableUser(user))
        } else {
            userDao.deleteUser()
        }
    }

    override fun observeAuthorization(): BehaviorRelay<Authorization?> {
        return currentAuthorizationRelay
    }

    /**
     * I recommend storing this in an encrypted form with a secret generated from Android's Keystore,
     * but didn't implement it here since this is just an example.
     *
     * If the user changes their phone's pin, it's ok if they have to login again.
     */
    override fun saveAuthorization(authorization: Authorization?) {
        saveOrRemoveSecureJsonData(AUTHORIZATION_KEY, authorization)
        currentAuthorizationRelay.onNext(authorization)
    }

    private fun getCurrentAuthorization(): Authorization? {
        return sharedPreferences.getString(AUTHORIZATION_KEY, null)?.let {
            gson.fromJson(it, Authorization::class.java)
        }
    }

    private fun <T> saveOrRemoveSecureJsonData(key: String, data: T?) {
        if (data != null)
            sharedPreferences.edit().putString(key, gson.toJson(data)).apply()
        else
            sharedPreferences.edit().remove(key).apply()
    }

    companion object {
        private const val SHARED_PREFERENCES_NAME = "starter_shared_prefs"
        private const val AUTHORIZATION_KEY = "authorization_key"
    }
}
