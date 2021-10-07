package com.somasoma.wagglewaggle.domain.repository

import com.somasoma.wagglewaggle.data.model.dto.world.WorldListResponse
import io.reactivex.Single
import retrofit2.Response

interface WorldRepository {
    fun getWorldList(): Single<Response<WorldListResponse>>
}