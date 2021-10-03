package com.somasoma.wagglewaggle.core.service

import com.somasoma.wagglewaggle.core.model.dto.auth.FirebaseRequest
import com.somasoma.wagglewaggle.core.model.dto.auth.FirebaseResponse
import com.somasoma.wagglewaggle.core.model.dto.auth.RefreshRequest
import com.somasoma.wagglewaggle.core.model.dto.auth.RefreshResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("test/auth/firebase")
    fun postFirebase(@Body firebaseRequest: FirebaseRequest): Single<Response<FirebaseResponse?>>

    @POST("test/auth/refresh")
    fun postRefresh(@Body refreshRequest: RefreshRequest): Single<Response<RefreshResponse?>>
}