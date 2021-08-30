package com.somasoma.speakworld.core.usecase

import com.somasoma.speakworld.core.model.User
import com.somasoma.speakworld.core.repository.UserRepository
import javax.inject.Inject

class WriteNewUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun writeNewUser(user: User, onSuccessCallback: () -> Unit) = userRepository.writeNewUser(user, onSuccessCallback)
}