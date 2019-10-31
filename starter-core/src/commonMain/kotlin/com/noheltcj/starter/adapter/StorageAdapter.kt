package com.noheltcj.starter.adapter

import com.noheltcj.starter.entity.*
import com.noheltcj.rxcommon.Source

interface StorageAdapter {
    fun observeUser(): Source<User?>
    fun saveUser(user: User?)

    /* Should be decrypted in implementation */
    fun observeAuthorization(): Source<Authorization?>

    /* Should be encrypted in implementation */
    fun saveAuthorization(authorization: Authorization?)
}