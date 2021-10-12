package com.somasoma.wagglewaggle.domain.usecase

import com.somasoma.wagglewaggle.domain.repository.AuthRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(private val authRepository: AuthRepository) {
    fun deleteAccount(onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit) =
        authRepository.deleteAccount(onSuccessCallback, onFailureCallback)
}