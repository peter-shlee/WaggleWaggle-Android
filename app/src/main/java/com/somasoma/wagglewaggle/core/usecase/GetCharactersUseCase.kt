package com.somasoma.wagglewaggle.core.usecase

import com.somasoma.wagglewaggle.core.model.Avatars
import com.somasoma.wagglewaggle.core.repository.AvatarsRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val avatarsRepository: AvatarsRepository) {
    fun getCharacters(callback: (Avatars) -> Unit) = avatarsRepository.getAvatars(callback)
}