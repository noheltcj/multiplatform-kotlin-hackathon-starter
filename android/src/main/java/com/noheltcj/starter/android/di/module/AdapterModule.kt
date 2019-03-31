package com.noheltcj.starter.android.di.module

import com.noheltcj.starter.adapter.*
import com.noheltcj.starter.android.adapter.*
import dagger.Binds
import dagger.Module

@Module
abstract class AdapterModule {
    @Binds abstract fun bindApiAdapter(apiAdapterImpl: ApiAdapterImpl): ApiAdapter
    @Binds abstract fun bindLocalizationAdapter(localizationAdapterImpl: LocalizationAdapterImpl): LocalizationAdapter
    @Binds abstract fun bindStorageAdapter(storageAdapterImpl: StorageAdapterImpl): StorageAdapter
    @Binds abstract fun bindApplicationStateAdapter(applicationStateAdapterImpl: ApplicationStateAdapterImpl): ApplicationStateAdapter
    @Binds abstract fun bindClockAdapter(clockAdapterImpl: ClockAdapterImpl): ClockAdapter
    @Binds abstract fun bindSchedulingAdapter(schedulingAdapter: SchedulingAdapterImpl): SchedulingAdapter
}
