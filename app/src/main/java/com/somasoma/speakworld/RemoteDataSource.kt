package com.somasoma.speakworld

interface RemoteDataSource {
    fun writeNewUser(userId: String, name: String, language: String)

    fun getUser(userId: String, callback: (User) -> Unit)

    fun deleteUser(userId: String, onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit)

    fun getAvatars(callback: (Avatars) -> Unit)

    fun setUserName(userId: String, newName: String)

    fun setUserLanguage(userId: String, newLanguage: String)

    fun setUserCountry(userId: String, newLanguage: String)
}