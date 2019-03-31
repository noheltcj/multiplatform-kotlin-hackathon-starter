package com.noheltcj.starter.android.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.noheltcj.starter.android.di.ViewModelFactory
import com.noheltcj.starter.android.di.ViewModelKey
import com.noheltcj.starter.android.ui.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModelAdapter::class)
    abstract fun bindLoginViewModelAdapter(loginViewModelAdapter: LoginViewModelAdapter): ViewModel

    @Binds abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}