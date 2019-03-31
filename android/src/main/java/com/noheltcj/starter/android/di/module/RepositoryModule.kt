package com.noheltcj.starter.android.di.module

import com.noheltcj.starter.android.adapter.repository.ApiRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun providesCoreApiRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .baseUrl("http://10.0.2.2:3000")
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun providesApiRepository(coreApiRetrofit: Retrofit): ApiRepository {
        return coreApiRetrofit.create(ApiRepository::class.java)
    }
}