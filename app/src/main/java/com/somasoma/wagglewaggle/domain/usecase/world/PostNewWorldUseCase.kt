package com.somasoma.wagglewaggle.domain.usecase.world

import com.somasoma.wagglewaggle.data.model.dto.world.WorldRoom
import com.somasoma.wagglewaggle.domain.repository.WorldRepository
import javax.inject.Inject

class PostNewWorldUseCase @Inject constructor(private val worldRepository: WorldRepository) {
    suspend fun postNewWorld(worldRoom: WorldRoom) = worldRepository.postNewWorld(worldRoom)
}