package com.noheltcj.starter.android.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.noheltcj.starter.android.ui.extension.toLiveData
import com.noheltcj.starter.presentation.LoginViewModel
import com.noheltcj.rxcommon.disposables.CompositeDisposeBag
import javax.inject.Inject

class LoginViewModelAdapter @Inject constructor(private val viewModel: LoginViewModel) : ViewModel() {
    private val disposeBag = CompositeDisposeBag()

    val navigationEvents = viewModel.navigationEvents.toLiveData(disposeBag)
    val showLoader = viewModel.showLoader.toLiveData(disposeBag)

    val emailField = viewModel.emailField.toLiveData(disposeBag)
    val emailFieldError = viewModel.emailFieldError.toLiveData(disposeBag)

    val passwordField = viewModel.passwordField.toLiveData(disposeBag)
    val passwordFieldError = viewModel.passwordFieldError.toLiveData(disposeBag)

    val submitEnabled = viewModel.submitEnabled.toLiveData(disposeBag)

    val showSignUp = MutableLiveData<Boolean>()

    fun onInit() { viewModel.onInit() }
    fun onLoginTapped() { viewModel.onLoginTapped() }
    fun onSignUpTapped() { viewModel.onSignUpTapped() }
    fun onForgotPasswordTapped() { viewModel.onForgotPasswordTapped() }
    fun onNavigationRecognized() { viewModel.onNavigationRecognized() }
    fun onShowSignUp() { showSignUp.postValue(true) }
    fun onHideSignUp() { showSignUp.postValue(false) }

    override fun onCleared() {
        disposeBag.dispose()
        viewModel.onCleanup()
        super.onCleared()
    }
}