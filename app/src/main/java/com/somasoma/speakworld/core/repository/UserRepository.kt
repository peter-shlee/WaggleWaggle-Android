package com.somasoma.speakworld.core.repository

import com.somasoma.speakworld.core.model.User

interface UserRepository {
    fun postUser(user: User, onSuccessCallback: () -> Unit)

    fun getUser(callback: (User) -> Unit)

    fun deleteUser(onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit)

    fun setUserName(name: String, onSuccessCallback: () -> Unit)

    fun setUserLanguage(language: String, onSuccessCallback: () -> Unit)
}