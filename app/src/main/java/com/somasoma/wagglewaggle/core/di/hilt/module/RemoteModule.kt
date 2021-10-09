package com.somasoma.wagglewaggle.core.di.hilt.module

import com.somasoma.wagglewaggle.data.repository_impl.AuthRepositoryImpl
import com.somasoma.wagglewaggle.data.repository_impl.MemberRepositoryImpl
import com.somasoma.wagglewaggle.data.repository_impl.WorldRepositoryImpl
import com.somasoma.wagglewaggle.domain.repository.AuthRepository
import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import com.somasoma.wagglewaggle.domain.repository.WorldRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RemoteModule {
    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindMemberRepository(memberRepositoryImpl: MemberRepositoryImpl): MemberRepository

    @Binds
    abstract fun bindWorldRepository(worldRepositoryImpl: WorldRepositoryImpl): WorldRepository
}