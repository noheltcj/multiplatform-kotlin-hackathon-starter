package com.noheltcj.starter.android.extension

import com.noheltcj.rxcommon.disposables.Disposables
import com.noheltcj.rxcommon.observables.Observable
import com.noheltcj.rxcommon.observables.Single
import com.noheltcj.starter.extension.Completable

fun <T> io.reactivex.Flowable<T>.toRxCommon(): Observable<T> {
    return Observable { commonEmitter ->
        val upstreamDisposable = subscribe({
            if (!commonEmitter.isDisposed) commonEmitter.next(it)
        }, {
            if (!commonEmitter.isDisposed) commonEmitter.terminate(it)
        }, {
            if (!commonEmitter.isDisposed) commonEmitter.complete()
        })
        Disposables.create {
            upstreamDisposable.dispose()
        }
    }
}

fun <T> io.reactivex.Single<T>.toRxCommon(): Single<T> {
    return Single { commonEmitter ->
        val upstreamDisposable = subscribe({
            commonEmitter.next(it)
        }, {
            commonEmitter.terminate(it)
        })
        Disposables.create {
            upstreamDisposable.dispose()
        }
    }
}

fun io.reactivex.Completable.toRxCommon(): Completable {
    return Single { commonEmitter ->
        val upstreamDisposable = subscribe({
            if (!commonEmitter.isDisposed) commonEmitter.next(Unit)
        }, {
            if (!commonEmitter.isDisposed) commonEmitter.terminate(it)
        })
        Disposables.create {
            upstreamDisposable.dispose()
        }
    }
}