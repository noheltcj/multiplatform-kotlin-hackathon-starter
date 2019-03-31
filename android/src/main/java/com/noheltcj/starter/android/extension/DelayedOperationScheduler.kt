package com.noheltcj.starter.android.extension

import com.noheltcj.starter.extension.DelayingScheduler
import io.reactivex.Completable
import java.util.concurrent.TimeUnit

class DelayedOperationScheduler : DelayingScheduler {
    override fun scheduleDelayed(duration: Long, operation: () -> Unit): DelayingScheduler.Cancelable {
        return object : DelayingScheduler.Cancelable {
            val disposable = Completable.complete()
                .delay(duration, TimeUnit.MILLISECONDS)
                .subscribe(operation)

            override fun cancel() {
                disposable.dispose()
            }
        }
    }
}