package com.somasoma.speakworld.core.usecase

import android.content.Context
import com.somasoma.speakworld.core.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val authRepository: AuthRepository) {
    fun signOut(context: Context, onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit) =
        authRepository.signOut(context, onSuccessCallback, onFailureCallback)
}