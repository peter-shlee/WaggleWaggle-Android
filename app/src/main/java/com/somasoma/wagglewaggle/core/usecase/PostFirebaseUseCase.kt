package com.somasoma.wagglewaggle.core.usecase

import com.somasoma.wagglewaggle.core.model.dto.auth.FirebaseRequest
import com.somasoma.wagglewaggle.core.repository.AuthRepository
import javax.inject.Inject

class PostFirebaseUseCase @Inject constructor(private val authRepository: AuthRepository) {
    fun postFirebase(firebaseRequest: FirebaseRequest) =
        authRepository.postFirebase(firebaseRequest)
}