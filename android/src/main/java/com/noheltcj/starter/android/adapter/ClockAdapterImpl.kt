package com.noheltcj.starter.android.adapter

import com.noheltcj.starter.adapter.ClockAdapter
import javax.inject.Inject

class ClockAdapterImpl @Inject constructor() : ClockAdapter {
    override val currentTimeEpochMilli: Long
        get() = System.currentTimeMillis()
}