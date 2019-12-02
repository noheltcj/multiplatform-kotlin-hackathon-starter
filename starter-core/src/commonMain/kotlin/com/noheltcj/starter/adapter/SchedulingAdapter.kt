package com.noheltcj.starter.adapter

import com.noheltcj.rxcommon.observables.Single

interface SchedulingAdapter {
    fun <E> scheduleConcurrent(operation: () -> E) : Single<E>
}