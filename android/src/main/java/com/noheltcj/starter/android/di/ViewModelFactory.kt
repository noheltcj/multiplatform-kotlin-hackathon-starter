package com.noheltcj.starter.android.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelFactory @Inject constructor(private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) = creators[modelClass]?.let { it.get() as T }
        ?: throw IllegalArgumentException("Unknown model class $modelClass")
}