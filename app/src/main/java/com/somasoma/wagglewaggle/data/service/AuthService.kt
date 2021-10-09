package com.somasoma.wagglewaggle.data.service

import com.somasoma.wagglewaggle.data.model.dto.auth.FirebaseRequest
import com.somasoma.wagglewaggle.data.model.dto.auth.FirebaseResponse
import com.somasoma.wagglewaggle.data.model.dto.auth.RefreshRequest
import com.somasoma.wagglewaggle.data.model.dto.auth.RefreshResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("test/auth/firebase")
    fun postFirebase(@Body firebaseRequest: FirebaseRequest): Single<Response<FirebaseResponse?>>

    @POST("test/auth/refresh")
    fun postRefresh(@Body refreshRequest: RefreshRequest): Single<Response<RefreshResponse?>>

    @POST("test/auth/firebase")
    suspend fun postFirebaseWithCoroutine(@Body firebaseRequest: FirebaseRequest): Response<FirebaseResponse?>

    @POST("test/auth/refresh")
    suspend fun postRefreshWithCoroutine(@Body refreshRequest: RefreshRequest): Response<RefreshResponse?>
}