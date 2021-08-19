package com.somasoma.speakworld.core.usecase

import com.somasoma.speakworld.core.repository.AuthRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(private val authRepository: AuthRepository) {
    fun deleteAccount(onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit) =
        authRepository.deleteAccount(onSuccessCallback, onFailureCallback)
}