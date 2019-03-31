package com.noheltcj.artist.presentation

import com.noheltcj.artist.adapter.ApiAdapter
import com.noheltcj.artist.adapter.LocalizationAdapter
import com.noheltcj.artist.domain.AuthInteractor
import com.noheltcj.artist.adapter.localization.StringResource
import com.noheltcj.artist.binding.BindingRelay
import com.noheltcj.artist.di.Inject
import com.noheltcj.artist.domain.ValidationInteractor
import com.noheltcj.artist.presentation.entity.FieldState
import com.noheltcj.rxcommon.disposables.CompositeDisposeBag
import com.noheltcj.rxcommon.observers.NextObserver
import com.noheltcj.rxcommon.observers.NextTerminalObserver
import com.noheltcj.rxcommon.operators.combineLatest
import com.noheltcj.rxcommon.subjects.BehaviorRelay

class LoginViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val validationInteractor: ValidationInteractor,
    private val localizationAdapter: LocalizationAdapter
) {
    private var emailFieldState = FieldState.PRISTINE
    private var passwordFieldState = FieldState.PRISTINE

    private val compositeDisposeBag = CompositeDisposeBag()

    val navigationEvents = BehaviorRelay<NavigationEvent>(NavigationEvent.Stay)

    val showLoader = BehaviorRelay(false)

    val emailField = BindingRelay("")
    val emailFieldError = BehaviorRelay<String?>(null)

    val passwordField = BindingRelay("")
    val passwordFieldError = BehaviorRelay<String?>(null)

    val submitEnabled = BehaviorRelay(false)

    fun onInit() {
        observeAndValidateFields()
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

    fun onCleared() {
        compositeDisposeBag.dispose()
    }

    private fun observeAndValidateFields() {
        val disposable = emailField.combineLatest(passwordField)
            .subscribe(NextObserver { (email, password) ->
                if (email.isNotBlank()) emailFieldState = FieldState.DIRTY
                if (password.isNotBlank()) passwordFieldState = FieldState.DIRTY

                if (emailFieldState == FieldState.DIRTY && !validationInteractor.isValidEmail(email)) {
                    emailFieldError.onNext(localizationAdapter[StringResource.LoginInvalidEmail])
                } else {
                    emailFieldError.onNext(null)
                }

                if (passwordFieldState == FieldState.DIRTY && !validationInteractor.isValidPassword(password)) {
                    passwordFieldError.onNext(localizationAdapter[StringResource.LoginInvalidPassword])
                } else {
                    passwordFieldError.onNext(null)
                }

                submitEnabled.onNext(emailFieldState == FieldState.DIRTY
                    && passwordFieldState == FieldState.DIRTY
                    && emailFieldError.value == null
                    && passwordFieldError.value == null)
            })
        compositeDisposeBag.add(disposable)
    }

    sealed class NavigationEvent {
        object Stay : NavigationEvent()
        object ShowHome : NavigationEvent()
        object ShowForgotPassword : NavigationEvent()
        data class ShowErrorAlert(val title: String, val message: String) : NavigationEvent()
    }
}