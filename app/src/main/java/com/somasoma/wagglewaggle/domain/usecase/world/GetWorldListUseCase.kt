package com.somasoma.wagglewaggle.domain.usecase.world

import com.somasoma.wagglewaggle.domain.repository.WorldRepository
import javax.inject.Inject

class GetWorldListUseCase @Inject constructor(private val worldRepository: WorldRepository) {
    suspend fun getWorldList(unit: Unit) = worldRepository.getWorldList()
}