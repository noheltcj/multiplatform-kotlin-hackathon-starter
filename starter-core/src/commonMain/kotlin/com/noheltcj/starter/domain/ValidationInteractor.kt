package com.noheltcj.starter.domain

interface ValidationInteractor {
    fun isValidEmail(email: String): Boolean
    fun isValidPassword(password: String): Boolean
}