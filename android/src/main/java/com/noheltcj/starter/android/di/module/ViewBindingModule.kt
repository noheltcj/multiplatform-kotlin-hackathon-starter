package com.noheltcj.starter.android.di.module

import com.noheltcj.starter.android.ui.activity.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewBindingModule {
    @ContributesAndroidInjector abstract fun contributesLoginActivityInjector(): LoginActivity
}
