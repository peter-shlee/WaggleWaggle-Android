package com.somasoma.speakworld.core.usecase

import com.somasoma.speakworld.core.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun deleteUser(onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit) =
        userRepository.deleteUser(onSuccessCallback, onFailureCallback)
}