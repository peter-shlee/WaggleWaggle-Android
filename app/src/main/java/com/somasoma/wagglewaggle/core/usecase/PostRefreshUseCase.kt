package com.somasoma.wagglewaggle.core.usecase

import com.somasoma.wagglewaggle.core.model.dto.auth.RefreshRequest
import com.somasoma.wagglewaggle.core.repository.AuthRepository
import javax.inject.Inject

class PostRefreshUseCase @Inject constructor(private val authRepository: AuthRepository) {
    fun postRefresh(refreshRequest: RefreshRequest) =
        authRepository.postRefresh(refreshRequest)
}