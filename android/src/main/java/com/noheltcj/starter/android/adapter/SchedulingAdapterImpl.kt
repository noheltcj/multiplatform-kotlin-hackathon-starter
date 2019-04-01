package com.noheltcj.starter.android.adapter

import com.noheltcj.rxcommon.disposables.Disposables
import com.noheltcj.starter.adapter.SchedulingAdapter
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

private typealias CommonSingle<E>  = com.noheltcj.rxcommon.observables.Single<E>

class SchedulingAdapterImpl @Inject constructor(
    @Named("main") private val mainScheduler: Scheduler,
    @Named("computation") private val computationScheduler: Scheduler
) : SchedulingAdapter {
    override fun <E> scheduleConcurrent(operation: () -> E): CommonSingle<E> {
        return CommonSingle(createWithEmitter = { commonEmitter ->
            val disposable = Single.create<E> { operation() }
                .subscribeOn(computationScheduler)
                .observeOn(mainScheduler)
                .subscribe({
                    if (!commonEmitter.isDisposed) {
                        commonEmitter.next(it)
                    }
                }, {
                    if (!commonEmitter.isDisposed) {
                        commonEmitter.terminate(it)
                    }
                })
            Disposables.create {
                disposable.dispose()
            }
        })
    }
}