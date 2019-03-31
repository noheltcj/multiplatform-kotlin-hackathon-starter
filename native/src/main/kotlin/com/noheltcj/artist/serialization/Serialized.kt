package com.noheltcj.starter.serialization

@Retention(AnnotationRetention.SOURCE)
actual annotation class Serialized(actual val value: String, actual val alternate: Array<String>)