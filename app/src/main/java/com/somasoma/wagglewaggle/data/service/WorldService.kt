package com.somasoma.wagglewaggle.data.service

import com.somasoma.wagglewaggle.data.model.dto.world.WorldListResponse
import retrofit2.Response
import retrofit2.http.GET

interface WorldService {
    @GET("world/world-room/list")
    suspend fun getWorldList(): Response<WorldListResponse>
}