package com.somasoma.speakworld.core.usecase

import android.content.Context
import com.somasoma.speakworld.core.repository.AuthRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(private val authRepository: AuthRepository) {
    fun deleteAccount(
        context: Context,
        onSuccessCallback: () -> Unit,
        onFailureCallback: () -> Unit
    ) = authRepository.deleteAccount(context, onSuccessCallback, onFailureCallback)
}