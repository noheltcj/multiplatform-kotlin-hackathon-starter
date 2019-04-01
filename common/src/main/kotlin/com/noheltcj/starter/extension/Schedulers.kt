package com.noheltcj.starter.extension

import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object Schedulers {
    lateinit var delayingScheduler: DelayingScheduler
}