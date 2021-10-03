package com.somasoma.wagglewaggle.core.usecase

import com.somasoma.wagglewaggle.core.repository.AuthRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(private val authRepository: AuthRepository) {
    fun deleteAccount(onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit) =
        authRepository.deleteAccount(onSuccessCallback, onFailureCallback)
}