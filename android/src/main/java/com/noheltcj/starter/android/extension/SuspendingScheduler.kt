package com.noheltcj.starter.android.extension

import com.noheltcj.starter.extension.Scheduler
import io.reactivex.Completable
import java.util.concurrent.TimeUnit

class SuspendingScheduler : Scheduler {
    override fun scheduleDelayed(duration: Long, operation: () -> Unit): Scheduler.Cancelable {
        return object : Scheduler.Cancelable {
            val disposable = Completable.complete()
                .delay(duration, TimeUnit.MILLISECONDS)
                .subscribe(operation)

            override fun cancel() {
                disposable.dispose()
            }
        }
    }
}