package com.somasoma.wagglewaggle.domain.repository

import com.somasoma.wagglewaggle.data.model.dto.auth.FirebaseRequest
import com.somasoma.wagglewaggle.data.model.dto.auth.FirebaseResponse
import com.somasoma.wagglewaggle.data.model.dto.auth.RefreshRequest
import com.somasoma.wagglewaggle.data.model.dto.auth.RefreshResponse
import retrofit2.Response

interface AuthRepository {
    fun signOut(onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit)
    fun deleteAccount(onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit)

    suspend fun postFirebase(firebaseRequest: FirebaseRequest): Response<FirebaseResponse?>
    suspend fun postRefresh(refreshRequest: RefreshRequest): Response<RefreshResponse?>
}