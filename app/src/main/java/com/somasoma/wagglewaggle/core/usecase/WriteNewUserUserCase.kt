package com.somasoma.wagglewaggle.core.usecase

import com.somasoma.wagglewaggle.core.model.User
import com.somasoma.wagglewaggle.core.repository.UserRepository
import javax.inject.Inject

class WriteNewUserUserCase @Inject constructor(private val userRepository: UserRepository) {
    fun writeNewUser(user: User, onSuccessCallback: () -> Unit) = userRepository.postUser(user, onSuccessCallback)
}