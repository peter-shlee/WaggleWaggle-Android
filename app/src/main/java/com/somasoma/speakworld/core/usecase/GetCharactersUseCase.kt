package com.somasoma.speakworld.core.usecase

import com.somasoma.speakworld.core.model.Avatars
import com.somasoma.speakworld.core.repository.AvatarsRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val avatarsRepository: AvatarsRepository) {
    fun getCharacters(callback: (Avatars) -> Unit) = avatarsRepository.getAvatars(callback)
}