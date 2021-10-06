package com.somasoma.wagglewaggle.domain.usecase

import com.somasoma.wagglewaggle.data.model.Avatars
import com.somasoma.wagglewaggle.data.repository.AvatarsRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val avatarsRepository: AvatarsRepository) {
    fun getCharacters(callback: (Avatars) -> Unit) = avatarsRepository.getAvatars(callback)
}