package com.noheltcj.starter.extension

interface Scheduler {
    fun scheduleDelayed(duration: Long, operation: () -> Unit): Scheduler.Cancelable

    interface Cancelable {
        fun cancel()
    }

    companion object {
        lateinit var instance: Scheduler
    }
}