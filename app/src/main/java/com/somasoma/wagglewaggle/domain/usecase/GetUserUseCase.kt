package com.somasoma.wagglewaggle.domain.usecase

import com.somasoma.wagglewaggle.data.model.User
import com.somasoma.wagglewaggle.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun getUser(callback: (User) -> Unit) = userRepository.getUser(callback)
}