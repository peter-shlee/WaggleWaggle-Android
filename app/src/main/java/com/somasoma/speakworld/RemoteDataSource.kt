package com.somasoma.speakworld

interface RemoteDataSource {
    fun writeNewUser(userId: String, name: String, language: String) {
    }

    fun getUser(userId: String, callback: (User) -> Unit)
}