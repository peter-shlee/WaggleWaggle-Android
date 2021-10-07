package com.somasoma.wagglewaggle.domain.usecase

import com.somasoma.wagglewaggle.domain.repository.UserRepository
import javax.inject.Inject

class SetUserLanguageUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun setUserLanguage(language: String, onSuccessCallback: () -> Unit) = userRepository.setUserLanguage(language, onSuccessCallback)
}