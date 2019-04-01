package com.noheltcj.starter.android.adapter

import com.noheltcj.rxcommon.disposables.Disposables
import com.noheltcj.rxcommon.emitters.SingleEmitter
import com.noheltcj.rxcommon.observables.Single
import com.noheltcj.rxcommon.subjects.PublishSubject
import com.noheltcj.rxcommon.subjects.Subject
import com.noheltcj.starter.adapter.ApplicationStateAdapter
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationStateAdapterImpl @Inject constructor() : ApplicationStateAdapter {
    private val backgroundStatusSubject: PublishSubject<Boolean> = PublishSubject()
    private val atomicInBackgroundState = AtomicBoolean()

    lateinit var readyEmitter: SingleEmitter<Unit>

    override var isInBackground: Boolean = false
        set(value) {
            if (atomicInBackgroundState.compareAndSet(!value, value)) {
                backgroundStatusSubject.onNext(value)
                field = value
            }
        }
    override val isInBackgroundSubject: Subject<Boolean> = backgroundStatusSubject

    override val applicationReady: Single<Unit> = Single(createWithEmitter = { emitter ->
        readyEmitter = emitter
        Disposables.empty()
    })
}
