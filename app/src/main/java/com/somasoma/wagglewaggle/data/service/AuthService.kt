package com.somasoma.wagglewaggle.data.service

import com.somasoma.wagglewaggle.data.model.dto.auth.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/firebase-token")
    suspend fun postFirebase(@Body firebaseRequest: FirebaseRequest): Response<FirebaseResponse?>

    @POST("auth/refresh")
    suspend fun postRefresh(@Body refreshRequest: RefreshRequest): Response<RefreshResponse?>

    @POST("auth/sign-up")
    suspend fun postSignUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse?>
}