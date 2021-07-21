package com.somasoma.speakworld

interface RemoteDataSource {
    fun writeNewUser(userId: String, name: String, language: String)

    fun getUser(userId: String, callback: (User) -> Unit)

    fun getCharacters(callback: (Characters) -> Unit)

    fun setUserName(userId: String, newName: String)

    fun setUserLanguage(userId: String, newLanguage: String)
}