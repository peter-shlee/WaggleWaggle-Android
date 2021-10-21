package com.somasoma.wagglewaggle.data.service

import com.somasoma.wagglewaggle.data.model.dto.world.WorldListResponse
import com.somasoma.wagglewaggle.data.model.dto.world.WorldRoom
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WorldService {
    @GET("world/world-room/list")
    suspend fun getWorldList(): Response<WorldListResponse?>

    @POST("world/world-room/new")
    suspend fun postNewWorld(@Body worldRoom: WorldRoom): Response<WorldRoom?>
}