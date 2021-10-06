package com.somasoma.wagglewaggle.domain.usecase

import com.somasoma.wagglewaggle.data.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun deleteUser(onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit) =
        userRepository.deleteUser(onSuccessCallback, onFailureCallback)
}