package com.noheltcj.starter.android.di.module

import com.noheltcj.starter.domain.AuthInteractor
import com.noheltcj.starter.domain.ValidationInteractor
import com.noheltcj.starter.domain.impl.AuthInteractorImpl
import com.noheltcj.starter.domain.impl.ValidationInteractorImpl
import dagger.Binds
import dagger.Module

@Module
abstract class InteractorModule {
    @Binds abstract fun bindAuthInteractor(authInteractorImpl: AuthInteractorImpl): AuthInteractor
    @Binds abstract fun bindValidationInteractor(validationInteractorImpl: ValidationInteractorImpl): ValidationInteractor
}