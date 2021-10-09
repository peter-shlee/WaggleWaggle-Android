package com.somasoma.wagglewaggle.domain.repository

import com.somasoma.wagglewaggle.data.model.dto.auth.FirebaseRequest
import com.somasoma.wagglewaggle.data.model.dto.auth.FirebaseResponse
import com.somasoma.wagglewaggle.data.model.dto.auth.RefreshRequest
import com.somasoma.wagglewaggle.data.model.dto.auth.RefreshResponse
import io.reactivex.Single
import retrofit2.Response

interface AuthRepository {
    fun signOut(onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit)
    fun deleteAccount(onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit)

    fun postFirebase(firebaseRequest: FirebaseRequest): Single<Response<FirebaseResponse?>>
    fun postRefresh(refreshRequest: RefreshRequest): Single<Response<RefreshResponse?>>
    suspend fun postFirebaseWithCoroutine(firebaseRequest: FirebaseRequest): Response<FirebaseResponse?>
    suspend fun postRefreshWithCoroutine(refreshRequest: RefreshRequest): Response<RefreshResponse?>
}