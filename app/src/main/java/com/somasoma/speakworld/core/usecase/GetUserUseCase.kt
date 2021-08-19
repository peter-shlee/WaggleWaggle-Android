package com.somasoma.speakworld.core.usecase

import com.somasoma.speakworld.core.model.User
import com.somasoma.speakworld.core.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun getUser(callback: (User) -> Unit) = userRepository.getUser(callback)
}