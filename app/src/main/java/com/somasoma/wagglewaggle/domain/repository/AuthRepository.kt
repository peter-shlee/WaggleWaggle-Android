package com.somasoma.wagglewaggle.domain.repository

import com.somasoma.wagglewaggle.data.model.dto.auth.*
import retrofit2.Response

interface AuthRepository {
    fun signOut(onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit)
    fun deleteAccount(onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit)

    suspend fun postFirebase(firebaseRequest: FirebaseRequest): Response<FirebaseResponse?>
    suspend fun postRefresh(refreshRequest: RefreshRequest): Response<RefreshResponse?>
    suspend fun postSignUp(signUpRequest: SignUpRequest): Response<SignUpResponse?>
}