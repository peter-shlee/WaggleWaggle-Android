package com.somasoma.wagglewaggle.core.usecase

import com.somasoma.wagglewaggle.core.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val authRepository: AuthRepository) {
    fun signOut(onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit) =
        authRepository.signOut(onSuccessCallback, onFailureCallback)
}