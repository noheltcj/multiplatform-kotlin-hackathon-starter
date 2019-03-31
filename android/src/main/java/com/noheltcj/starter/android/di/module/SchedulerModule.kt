package com.noheltcj.starter.android.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

@Module
class SchedulerModule {
    @Provides
    @Named("main")
    fun providesMainScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Named("computation")
    fun providesComputationScheduler(): Scheduler = Schedulers.computation()
}