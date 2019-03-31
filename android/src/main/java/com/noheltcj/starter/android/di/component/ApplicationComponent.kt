package com.noheltcj.starter.android.di.component

import com.noheltcj.starter.android.di.module.*
import com.noheltcj.starter.android.ui.MainApplication
import dagger.Component
import dagger.MembersInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    ViewBindingModule::class,
    ViewModelModule::class,
    RepositoryModule::class,
    AdapterModule::class,
    InteractorModule::class,
    SchedulerModule::class,
    DatabaseModule::class,
    AndroidSupportInjectionModule::class
])
interface ApplicationComponent : MembersInjector<MainApplication>