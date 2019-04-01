package com.noheltcj.starter.android.ui

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.noheltcj.starter.android.adapter.ApplicationStateAdapterImpl
import javax.inject.Inject

class ActivityLifecycleObserver @Inject constructor(
    private val applicationStateAdapter: ApplicationStateAdapterImpl
): Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity?) {}
    override fun onActivityResumed(activity: Activity?) {
        applicationStateAdapter.isInBackground = false
    }
    override fun onActivityStarted(activity: Activity?) {
        if (!applicationStateAdapter.readyEmitter.isDisposed) {
            applicationStateAdapter.readyEmitter.next(Unit)
        }
    }
    override fun onActivityDestroyed(activity: Activity?) {}
    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
    override fun onActivityStopped(activity: Activity?) {}
    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {}
}