package com.noheltcj.starter.domain.impl

import com.noheltcj.starter.di.Inject
import com.noheltcj.starter.domain.ValidationInteractor

class ValidationInteractorImpl @Inject constructor() : ValidationInteractor {
    override fun isValidEmail(email: String): Boolean = email.matches(emailRegex)
    override fun isValidPassword(password: String): Boolean = password.length > 7

    companion object {
        val emailRegex = Regex("^.+@.+\\..{2,}$")
    }
}