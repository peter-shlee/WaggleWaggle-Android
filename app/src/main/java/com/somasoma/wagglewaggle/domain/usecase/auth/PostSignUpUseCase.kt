package com.somasoma.wagglewaggle.domain.usecase.auth

import com.somasoma.wagglewaggle.data.model.dto.auth.SignUpRequest
import com.somasoma.wagglewaggle.domain.repository.AuthRepository
import javax.inject.Inject

class PostSignUpUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun postSignUp(signUpRequest: SignUpRequest) = authRepository.postSignUp(signUpRequest)
}