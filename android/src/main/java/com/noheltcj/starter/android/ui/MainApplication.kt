package com.noheltcj.starter.android.ui

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.fragment.app.Fragment
import com.noheltcj.starter.android.di.component.DaggerApplicationComponent
import com.noheltcj.starter.android.di.module.ApplicationModule
import com.noheltcj.starter.android.extension.SuspendingScheduler
import com.noheltcj.starter.extension.Scheduler
import com.noheltcj.starter.presentation.ApplicationViewModel
import com.noheltcj.rxcommon.observers.NextObserver
import com.noheltcj.starter.android.ui.activity.LoginActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainApplication : Application(), HasSupportFragmentInjector, HasActivityInjector {
    @Inject lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject lateinit var activityLifecycleObserver: ActivityLifecycleObserver
    @Inject lateinit var applicationCallbacks: ApplicationCallbacks

    @Inject lateinit var viewModel: ApplicationViewModel

    override fun onCreate() {
        super.onCreate()

        Scheduler.instance = SuspendingScheduler()

        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
            .injectMembers(this)

        registerComponentCallbacks(applicationCallbacks)
        registerActivityLifecycleCallbacks(activityLifecycleObserver)

        observeNavigationOverrides()

        viewModel.onInit()
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingFragmentInjector

    private fun observeNavigationOverrides() {
        viewModel.navigationOverrideEvents.subscribe(NextObserver {
            when (it) {
                ApplicationViewModel.NavigationOverrideEvent.None -> {}
                ApplicationViewModel.NavigationOverrideEvent.ShowLogin -> {
                    startActivity(Intent(this, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
                }
            }
        })
    }
}