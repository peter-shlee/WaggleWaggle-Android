package com.somasoma.wagglewaggle.domain.repository

import com.somasoma.wagglewaggle.data.model.dto.world.WorldListResponse
import retrofit2.Response

interface WorldRepository {
    suspend fun getWorldList(): Response<WorldListResponse?>
}