package com.noheltcj.starter.presentation

import com.noheltcj.starter.adapter.ApiAdapter
import com.noheltcj.starter.adapter.LocalizationAdapter
import com.noheltcj.starter.domain.AuthInteractor
import com.noheltcj.starter.adapter.localization.StringResource
import com.noheltcj.starter.binding.BindingRelay
import com.noheltcj.starter.di.Inject
import com.noheltcj.starter.domain.ValidationInteractor
import com.noheltcj.rxcommon.disposables.CompositeDisposeBag
import com.noheltcj.rxcommon.observables.Single
import com.noheltcj.rxcommon.observers.NextTerminalObserver
import com.noheltcj.rxcommon.operators.combineLatest
import com.noheltcj.rxcommon.subjects.BehaviorRelay
import com.noheltcj.starter.extension.*

class LoginViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val validationInteractor: ValidationInteractor,
    private val localizationAdapter: LocalizationAdapter
) {
    private val compositeDisposeBag = CompositeDisposeBag()

    private lateinit var emailTouched: Single<Unit>
    private lateinit var passwordTouched: Single<Unit>

    val navigationEvents = BehaviorRelay<NavigationEvent>(NavigationEvent.Stay)

    val showLoader = BehaviorRelay(false)

    val emailField = BindingRelay("")
    val emailFieldError = BehaviorRelay<String?>(null)

    val passwordField = BindingRelay("")
    val passwordFieldError = BehaviorRelay<String?>(null)

    val submitEnabled = BehaviorRelay(false)

    fun onInit() {
        observeAndValidateEmail()
        observeAndValidatePassword()
        observeSubmissionConcerns()
    }

    fun onLoginTapped() {
        submitEnabled.onNext(false)
        showLoader.onNext(true)

        authInteractor.signIn(emailField.value, passwordField.value)
            .subscribe(NextTerminalObserver({
                showLoader.onNext(false)
                navigationEvents.onNext(NavigationEvent.ShowHome)
            }, {
                showLoader.onNext(false)
                submitEnabled.onNext(true)
                navigationEvents.onNext(NavigationEvent.ShowErrorAlert(
                    title = localizationAdapter[StringResource.GenericErrorTitle],
                    message = localizationAdapter[
                        (it as? ApiAdapter.UnexpectedResponseException)?.let { responseException ->
                            when(responseException.statusCode) {
                                401 -> StringResource.LoginInvalidCredentials
                                else -> StringResource.GenericErrorMessage
                            }
                        } ?: StringResource.GenericErrorMessage
                    ]
                ))
            }))
    }

    fun onSignUpTapped() {
        submitEnabled.onNext(false)
        showLoader.onNext(true)

        authInteractor.signUp(emailField.value, passwordField.value)
            .subscribe(NextTerminalObserver({
                showLoader.onNext(false)
                navigationEvents.onNext(NavigationEvent.ShowHome)
            }, {
                showLoader.onNext(false)
                submitEnabled.onNext(true)
                navigationEvents.onNext(LoginViewModel.NavigationEvent.ShowErrorAlert(
                    title = localizationAdapter[StringResource.GenericErrorTitle],
                    message = localizationAdapter[StringResource.GenericErrorMessage]
                ))
            }))
    }

    fun onForgotPasswordTapped() {
        passwordField.onNext("")
        navigationEvents.onNext(NavigationEvent.ShowForgotPassword)
    }

    fun onNavigationRecognized() {
        navigationEvents.onNext(NavigationEvent.Stay)
    }

    fun onCleanup() {
        compositeDisposeBag.dispose()
    }

    private fun observeAndValidateEmail() {
        emailTouched = emailField.filter { it.isNotBlank() }.first().toCompletable()
        val validationError = emailField.combineLatest(emailTouched)
            .debounce(VALIDATION_DELAY)
            .map { (email, _) ->
                if (validationInteractor.isValidEmail(email)) {
                    null
                } else {
                    localizationAdapter[StringResource.LoginInvalidEmail]
                }
            }
        compositeDisposeBag.add(emailFieldError.subscribeTo(validationError))
    }

    private fun observeAndValidatePassword() {
        passwordTouched = passwordField.filter { it.isNotBlank() }.first().toCompletable()
        val validationError = passwordField.combineLatest(passwordTouched)
            .debounce(VALIDATION_DELAY)
            .map { (email, _) ->
                if (validationInteractor.isValidPassword(email)) {
                    null
                } else {
                    localizationAdapter[StringResource.LoginInvalidPassword]
                }
            }
        compositeDisposeBag.add(passwordFieldError.subscribeTo(validationError))
    }

    private fun observeSubmissionConcerns() {
        val validEmailSource = emailTouched.combineLatest(emailFieldError) { _, error -> error == null }
        val validPasswordSource = passwordTouched.combineLatest(passwordFieldError) { _, error -> error == null }
        val enableSubmitSource = validEmailSource.combineLatest(validPasswordSource) { validEmail, validPassword ->
            validEmail && validPassword
        }
        compositeDisposeBag.add(submitEnabled.subscribeTo(enableSubmitSource))
    }

    sealed class NavigationEvent {
        object Stay : NavigationEvent()
        object ShowHome : NavigationEvent()
        object ShowForgotPassword : NavigationEvent()
        data class ShowErrorAlert(val title: String, val message: String) : NavigationEvent()
    }

    companion object {
        const val VALIDATION_DELAY = 400L
    }
}