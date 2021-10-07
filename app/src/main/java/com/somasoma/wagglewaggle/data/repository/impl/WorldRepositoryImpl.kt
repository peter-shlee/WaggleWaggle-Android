package com.somasoma.wagglewaggle.data.repository.impl

import com.somasoma.wagglewaggle.core.di.hilt.qualifier.ForWorldAPI
import com.somasoma.wagglewaggle.data.repository.WorldRepository
import com.somasoma.wagglewaggle.data.service.WorldService
import retrofit2.Retrofit
import javax.inject.Inject

class WorldRepositoryImpl @Inject constructor(@ForWorldAPI private val worldRetrofit: Retrofit) :
    WorldRepository {

    private val worldService = worldRetrofit.create(WorldService::class.java)

    override fun getWorldList() = worldService.getWorldList()
}