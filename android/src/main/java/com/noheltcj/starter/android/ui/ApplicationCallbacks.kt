package com.noheltcj.starter.android.ui

import android.content.ComponentCallbacks2
import android.content.res.Configuration
import com.noheltcj.starter.adapter.ApplicationStateAdapter
import javax.inject.Inject

class ApplicationCallbacks @Inject constructor(
    private val applicationStateAdapter: ApplicationStateAdapter
): ComponentCallbacks2 {
    override fun onLowMemory() {}
    override fun onConfigurationChanged(newConfig: Configuration?) {}
    override fun onTrimMemory(level: Int) {
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN)
            applicationStateAdapter.isInBackground = true
    }
}