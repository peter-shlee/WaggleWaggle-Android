package com.somasoma.speakworld.core.usecase

import com.somasoma.speakworld.core.repository.UserRepository
import javax.inject.Inject

class SetUserNameUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun setUserName(name: String, onSuccessCallback: () -> Unit) = userRepository.setUserName(name, onSuccessCallback)
}