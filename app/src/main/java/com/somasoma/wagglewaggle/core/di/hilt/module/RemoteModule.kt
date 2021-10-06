package com.somasoma.wagglewaggle.core.di.hilt.module

import com.somasoma.wagglewaggle.core.repository.*
import com.somasoma.wagglewaggle.core.repository.firebase_repository.FirebaseAvatarsRepository
import com.somasoma.wagglewaggle.core.repository.firebase_repository.FirebaseUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RemoteModule {
    @Binds
    abstract fun bindCharactersRepository(firebaseCharactersRepository: FirebaseAvatarsRepository): AvatarsRepository

    @Binds
    abstract fun bindUserRepository(firebaseUserRepository: FirebaseUserRepository): UserRepository

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindMemberRepository(memberRepositoryImpl: MemberRepositoryImpl): MemberRepository
}