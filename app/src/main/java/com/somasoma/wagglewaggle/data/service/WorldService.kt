package com.somasoma.wagglewaggle.data.service

import com.somasoma.wagglewaggle.data.model.dto.world.WorldListResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface WorldService {
    @GET("world/world-room/list")
    fun getWorldList(): Single<Response<WorldListResponse>>

    @GET("world/world-room/list")
    suspend fun getWorldListWithCoroutine(): Response<WorldListResponse>
}