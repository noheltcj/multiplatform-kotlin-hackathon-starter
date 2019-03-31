package com.noheltcj.starter

import com.noheltcj.rxcommon.disposables.CompositeDisposeBag
import com.noheltcj.rxcommon.disposables.Disposables
import com.noheltcj.rxcommon.observables.Observable
import com.noheltcj.rxcommon.observers.AllObserver
import com.noheltcj.rxcommon.observers.NextObserver
import com.noheltcj.rxcommon.observers.NextTerminalObserver

/**
 * This class is never intended to be instantiated.
 * Without this, the compiler will omit these classes from the generated native library headers.
 *
 * These are necessary for the iOS app.
 */
class Keeper {
    lateinit var compositeDisposeBag: CompositeDisposeBag
    lateinit var nextObserver: NextObserver<Any>
    lateinit var nextTerminalObserver: NextTerminalObserver<Any>
    lateinit var allObserver: AllObserver<Any>
    lateinit var observable: Observable<Any>
    lateinit var disposables: Disposables
}
