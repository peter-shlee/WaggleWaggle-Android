package com.somasoma.speakworld.core.usecase

import com.somasoma.speakworld.core.repository.UserRepository
import javax.inject.Inject

class SetUserLanguageUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun setUserLanguage(language: String, onSuccessCallback: () -> Unit) = userRepository.setUserLanguage(language, onSuccessCallback)
}