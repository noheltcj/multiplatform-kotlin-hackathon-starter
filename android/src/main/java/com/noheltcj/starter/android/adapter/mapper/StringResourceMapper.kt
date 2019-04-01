package com.noheltcj.starter.android.adapter.mapper

import com.noheltcj.starter.adapter.localization.StringResource
import com.noheltcj.starter.android.R
import javax.inject.Inject

class StringResourceMapper @Inject constructor() {
    fun map(resource: StringResource) = when(resource) {
        StringResource.GenericErrorTitle -> R.string.generic_error_title
        StringResource.GenericErrorMessage -> R.string.generic_error_message
        StringResource.LoginInvalidEmail -> R.string.login_invalid_email
        StringResource.LoginInvalidPassword -> R.string.login_invalid_password
        StringResource.LoginInvalidCredentials -> R.string.login_invalid_credentials
    }
}