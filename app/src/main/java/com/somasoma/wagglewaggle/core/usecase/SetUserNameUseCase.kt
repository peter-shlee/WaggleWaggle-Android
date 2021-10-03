package com.somasoma.wagglewaggle.core.usecase

import com.somasoma.wagglewaggle.core.repository.UserRepository
import javax.inject.Inject

class SetUserNameUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun setUserName(name: String, onSuccessCallback: () -> Unit) = userRepository.setUserName(name, onSuccessCallback)
}