package com.noheltcj.starter.android.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.noheltcj.rxcommon.disposables.CompositeDisposeBag
import com.noheltcj.rxcommon.disposables.Disposables
import com.noheltcj.rxcommon.observers.NextObserver
import com.noheltcj.rxcommon.subjects.Subject
import com.noheltcj.starter.binding.BindingRelay
import com.noheltcj.starter.binding.InlineBiDirectionalBinding

fun <E> Subject<E>.toLiveData(disposeBag: CompositeDisposeBag): LiveData<E> {
    val liveData = MutableLiveData<E>()

    disposeBag.add(subscribe(NextObserver {
        liveData.postValue(it)
    }))

    return liveData
}

fun <E> BindingRelay<E>.toMutableLiveData(disposeBag: CompositeDisposeBag): MutableLiveData<E> {
    val liveData = MutableLiveData<E>()
    val binding = InlineBiDirectionalBinding(
            onNext = { emission ->
                liveData.postValue(emission)
            },
            relay = this,
            transformForUpstreamBlock = { it },
            transformForSubscriberBlock = { it }
    )
    val observer = Observer<E> {
        binding.onSubscriberUpdatedLocally(it)
    }

    disposeBag.add(binding)
    disposeBag.add(Disposables.create {
        liveData.removeObserver(observer)
    })

    liveData.observeForever(observer)
    binding.start()

    return liveData
}