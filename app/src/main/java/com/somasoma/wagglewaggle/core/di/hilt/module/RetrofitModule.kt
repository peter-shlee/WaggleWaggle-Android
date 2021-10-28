package com.somasoma.wagglewaggle.core.di.hilt.module

import com.somasoma.wagglewaggle.core.di.hilt.qualifier.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    companion object {
        const val API_SERVER_BASE_URL = "https://www.waggle-waggle.com/"
    }

    @Provides
    @Singleton
    @ForAuthAPI
    fun provideAuthRetrofit(@ForAccessHttp accessHttpClient: OkHttpClient, @ForPublicHttp publicHttpClient: OkHttpClient): Pair<Retrofit, Retrofit> {
        val retrofit =
            Retrofit.Builder()
                .baseUrl(API_SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(accessHttpClient)
                .build()

        val publicRetrofit =
            Retrofit.Builder()
                .baseUrl(API_SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(publicHttpClient)
                .build()

        return retrofit to publicRetrofit
    }

    @Provides
    @Singleton
    @ForMemberAPI
    fun provideMemberRetrofit(
        @ForAccessHttp accessHttpClient: OkHttpClient,
        @ForPublicHttp publicHttpClient: OkHttpClient
    ): Pair<Retrofit, Retrofit> {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_SERVER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(accessHttpClient)
            .build()

        val publicRetrofit = Retrofit.Builder()
            .baseUrl(API_SERVER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(publicHttpClient)
            .build()

        return retrofit to publicRetrofit
    }

    @Provides
    @Singleton
    @ForWorldAPI
    fun provideWorldRetrofit(@ForAccessHttp accessHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_SERVER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(accessHttpClient)
            .build()
    }
}