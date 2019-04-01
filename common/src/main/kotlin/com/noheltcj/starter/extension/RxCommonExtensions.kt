package com.noheltcj.starter.extension

import com.noheltcj.rxcommon.observables.Single
import com.noheltcj.rxcommon.Source
import com.noheltcj.rxcommon.observers.AllObserver

typealias Completable = Single<Unit>

fun <E, O> Source<E>.beginChainedCombineLatest(otherSource: Source<O>): Source<MutableList<Any?>> {
    return combineLatest(otherSource) { first, second ->
        mutableListOf(first, second)
    }
}

fun <E, O: E> Source<MutableList<E>>.chainedCombineLatest(otherSource: Source<O>): Source<MutableList<E>> {
    return combineLatest(otherSource) { mutableList, newElement ->
        mutableList.add(newElement)
        mutableList
    }
}

fun <E> Source<E>.debounce(timespan: Long) = Debounce(timespan, Schedulers.delayingScheduler, this)

fun <E> Source<E?>.filterNotNull() =
    filter {
        it != null
    }.map {
        it!!
    }

fun <E> Source<E>.toCompletable(): Completable {
    return Completable(createWithEmitter = { completableEmitter ->
        subscribe(AllObserver({}, {
            completableEmitter.terminate(it)
        }, {
            completableEmitter.next(Unit)
        }))
    })
}