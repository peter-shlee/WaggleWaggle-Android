package com.somasoma.speakworld.core.usecase

import com.somasoma.speakworld.core.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val authRepository: AuthRepository) {
    fun signOut(onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit) =
        authRepository.signOut(onSuccessCallback, onFailureCallback)
}