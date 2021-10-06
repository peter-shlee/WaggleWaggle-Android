package com.somasoma.wagglewaggle.domain.usecase

import com.somasoma.wagglewaggle.data.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val authRepository: AuthRepository) {
    fun signOut(onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit) =
        authRepository.signOut(onSuccessCallback, onFailureCallback)
}