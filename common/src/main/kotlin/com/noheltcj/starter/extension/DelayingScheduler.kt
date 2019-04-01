package com.noheltcj.starter.extension

interface DelayingScheduler {
    fun scheduleDelayed(duration: Long, operation: () -> Unit): DelayingScheduler.Cancelable

    interface Cancelable {
        fun cancel()
    }

    companion object {
        lateinit var instance: DelayingScheduler
    }
}