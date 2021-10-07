package com.somasoma.wagglewaggle.domain.usecase

import com.somasoma.wagglewaggle.data.model.TmpAvatars
import com.somasoma.wagglewaggle.domain.repository.AvatarsRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val avatarsRepository: AvatarsRepository) {
    fun getCharacters(callback: (TmpAvatars) -> Unit) = avatarsRepository.getAvatars(callback)
}