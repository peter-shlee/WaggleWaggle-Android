package com.somasoma.speakworld

import com.somasoma.speakworld.core.model.Avatars
import com.somasoma.speakworld.core.model.User

interface RemoteDataSource {
    fun writeNewUser(userId: String, name: String, language: String)

    fun getUser(userId: String, callback: (User) -> Unit)

    fun deleteUser(userId: String, onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit)

    fun getCharacters(callback: (Avatars) -> Unit)

    fun setUserName(userId: String, newName: String)

    fun setUserLanguage(userId: String, newLanguage: String)
}