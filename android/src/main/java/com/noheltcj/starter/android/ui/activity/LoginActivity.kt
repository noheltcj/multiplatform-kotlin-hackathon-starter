package com.noheltcj.starter.android.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.noheltcj.starter.android.BR
import com.noheltcj.starter.android.R
import com.noheltcj.starter.android.databinding.ActivityLoginBinding
import com.noheltcj.starter.android.di.ViewModelFactory
import com.noheltcj.starter.presentation.LoginViewModel
import com.noheltcj.starter.android.ui.viewmodel.LoginViewModelAdapter
import dagger.android.AndroidInjection.inject

import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[LoginViewModelAdapter::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject(this)

        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login).also {
            it.setVariable(BR.vm, viewModel)
            it.lifecycleOwner = this
        }

        login_password_field.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                viewModel.onLoginTapped()
                return@OnEditorActionListener true
            }
            false
        })

        observeNavigationEvents()

        viewModel.onInit()
    }

    private fun observeNavigationEvents() {
        viewModel.navigationEvents.observe(this, Observer {
            when(it) {
                LoginViewModel.NavigationEvent.Stay -> {}
                LoginViewModel.NavigationEvent.ShowHome -> {
                    AlertDialog.Builder(this)
                        .setTitle("Hooray")
                        .setMessage("Nailed the credentials!")
                        .show()
                    viewModel.onNavigationRecognized()
                }
                LoginViewModel.NavigationEvent.ShowForgotPassword -> {
                    AlertDialog.Builder(this)
                        .setTitle("Forgot password")
                        .setMessage("Pretend this is here")
                        .show()
                    viewModel.onNavigationRecognized()
                }
                is LoginViewModel.NavigationEvent.ShowErrorAlert -> {
                    Snackbar.make(login_coordinator, it.message, Snackbar.LENGTH_LONG).apply {
                        setAction(R.string.ok) { }
                        show()
                    }
                    viewModel.onNavigationRecognized()
                }
            }
        })
    }
}
