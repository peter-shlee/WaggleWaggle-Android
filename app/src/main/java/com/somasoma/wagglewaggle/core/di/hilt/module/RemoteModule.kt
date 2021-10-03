package com.somasoma.wagglewaggle.core.di.hilt.module

import com.somasoma.wagglewaggle.core.repository.AuthRepository
import com.somasoma.wagglewaggle.core.repository.AvatarsRepository
import com.somasoma.wagglewaggle.core.repository.UserRepository
import com.somasoma.wagglewaggle.core.repository.firebase_repository.FirebaseAuthRepository
import com.somasoma.wagglewaggle.core.repository.firebase_repository.FirebaseAvatarsRepository
import com.somasoma.wagglewaggle.core.repository.firebase_repository.FirebaseUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class RemoteModule {
    @Binds
    abstract fun bindCharactersRepository(firebaseCharactersRepository: FirebaseAvatarsRepository): AvatarsRepository

    @Binds
    abstract fun bindUserRepository(firebaseUserRepository: FirebaseUserRepository): UserRepository

    @Binds
    abstract fun bindAuthRepository(firebaseAuthRepository: FirebaseAuthRepository): AuthRepository
}