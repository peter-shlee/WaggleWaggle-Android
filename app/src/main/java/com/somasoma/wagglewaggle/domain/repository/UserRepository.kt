package com.somasoma.wagglewaggle.domain.repository

import com.somasoma.wagglewaggle.data.model.User

interface UserRepository {
    fun postUser(user: User, onSuccessCallback: () -> Unit)

    fun getUser(callback: (User) -> Unit)

    fun deleteUser(onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit)

    fun setUserName(name: String, onSuccessCallback: () -> Unit)

    fun setUserLanguage(language: String, onSuccessCallback: () -> Unit)
}