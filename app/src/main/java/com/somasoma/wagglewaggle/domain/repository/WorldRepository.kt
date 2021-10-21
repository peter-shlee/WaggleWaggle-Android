package com.somasoma.wagglewaggle.domain.repository

import com.somasoma.wagglewaggle.data.model.dto.world.WorldListResponse
import com.somasoma.wagglewaggle.data.model.dto.world.WorldRoom
import retrofit2.Response

interface WorldRepository {
    suspend fun getWorldList(): Response<WorldListResponse?>
    suspend fun postNewWorld(worldRoom: WorldRoom): Response<WorldRoom?>
}