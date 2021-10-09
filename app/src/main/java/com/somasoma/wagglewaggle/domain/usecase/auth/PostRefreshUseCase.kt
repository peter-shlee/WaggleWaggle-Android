package com.somasoma.wagglewaggle.domain.usecase.auth

import com.somasoma.wagglewaggle.data.model.dto.auth.RefreshRequest
import com.somasoma.wagglewaggle.domain.repository.AuthRepository
import javax.inject.Inject

class PostRefreshUseCase @Inject constructor(private val authRepository: AuthRepository) {
    fun postRefresh(refreshRequest: RefreshRequest) =
        authRepository.postRefresh(refreshRequest)

    suspend fun postRefreshWithCoroutine(refreshRequest: RefreshRequest) =
        authRepository.postRefreshWithCoroutine(refreshRequest)
}