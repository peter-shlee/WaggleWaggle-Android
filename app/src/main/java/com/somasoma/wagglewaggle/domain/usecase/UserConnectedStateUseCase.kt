package com.somasoma.wagglewaggle.domain.usecase

import com.somasoma.wagglewaggle.data.repository.firebase_repository.FirebaseConnectedStateRepository
import javax.inject.Inject

class UserConnectedStateUseCase @Inject constructor(private val firebaseConnectedStateRepository: FirebaseConnectedStateRepository) {
    fun registerOnUserConnectedStateCallback(
        onFailureCallback: () -> Unit
    ) = firebaseConnectedStateRepository.registerOnUserConnectedStateCallback(
        onFailureCallback
    )

    fun postCurrentUserOnline() = firebaseConnectedStateRepository.postCurrentUserOnline()

    fun postCurrentUserOffline() = firebaseConnectedStateRepository.postCurrentUserOffline()
}