package com.somasoma.wagglewaggle.core.usecase

import com.somasoma.wagglewaggle.core.model.User
import com.somasoma.wagglewaggle.core.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun getUser(callback: (User) -> Unit) = userRepository.getUser(callback)
}