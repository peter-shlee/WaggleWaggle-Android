package com.somasoma.wagglewaggle.domain.usecase

import com.somasoma.wagglewaggle.data.model.User
import com.somasoma.wagglewaggle.domain.repository.UserRepository
import javax.inject.Inject

class WriteNewUserUserCase @Inject constructor(private val userRepository: UserRepository) {
    fun writeNewUser(user: User, onSuccessCallback: () -> Unit) = userRepository.postUser(user, onSuccessCallback)
}