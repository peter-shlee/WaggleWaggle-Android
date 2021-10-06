package com.somasoma.wagglewaggle.domain.usecase

import com.somasoma.wagglewaggle.data.repository.UserRepository
import javax.inject.Inject

class SetUserNameUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun setUserName(name: String, onSuccessCallback: () -> Unit) = userRepository.setUserName(name, onSuccessCallback)
}