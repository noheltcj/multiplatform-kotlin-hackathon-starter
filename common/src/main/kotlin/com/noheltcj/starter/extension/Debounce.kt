package com.noheltcj.starter.extension

import com.noheltcj.rxcommon.Source
import com.noheltcj.rxcommon.disposables.Disposable
import com.noheltcj.rxcommon.emitters.ColdEmitter
import com.noheltcj.rxcommon.emitters.Emitter
import com.noheltcj.rxcommon.observers.AllObserver
import com.noheltcj.rxcommon.observers.Observer
import com.noheltcj.rxcommon.operators.Operator

class Debounce<E>(
    private val durationInMillis: Long,
    private val scheduler: DelayingScheduler,
    private val source: Source<E>
) : Operator<E>() {
    private var cancelable: DelayingScheduler.Cancelable? = null

    override val emitter: Emitter<E> = ColdEmitter {
        cancelable?.cancel()
    }

    override fun subscribe(observer: Observer<E>): Disposable {
        emitter.addObserver(observer)
        return source.subscribe(AllObserver(
            onNext = {
                cancelable?.cancel()
                cancelable = scheduler.scheduleDelayed(durationInMillis) {
                    emitter.next(it)
                }
            },
            onError = {
                emitter.terminate(it)
            },
            onComplete = {
                emitter.complete()
            }
        ))
    }
}