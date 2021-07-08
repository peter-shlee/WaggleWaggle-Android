package com.somasoma.speakworld

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber
import javax.inject.Inject

class SetLanguageViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private val remoteDataSource: RemoteDataSource = FirebaseRealtimeDB

    private var firebaseUser: FirebaseUser? = null

    private var userId: String = ""
    var name: String? = null
    var language: String = ""

    fun signUp() {
        Timber.d("signUp()%s", userId)
        name?.let {
            if (userId != "" && it != "" && language != "") {
                remoteDataSource.writeNewUser(userId, it, language)
            }
        }
    }

    fun setFirebaseUser(user: FirebaseUser?) {
        Timber.d(user.toString())
        firebaseUser = user
        user?.let {
            Timber.d("%shello", it.uid)
            userId = it.uid
        }
    }

    fun getFirebaseUser(): FirebaseUser? {
        return firebaseUser
    }
}