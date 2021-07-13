package com.somasoma.speakworld

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber
import javax.inject.Inject

class SignUpSetLanguageViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private val remoteDataSource: RemoteDataSource = FirebaseRealtimeDB

    val user = FirebaseAuth.getInstance().currentUser

    private var userId = user?.uid
    var name: String? = null
    var language: String = ""

    fun signUp() {
        Timber.d("signUp()%s", userId)
        name?.let { name ->
            userId?.let { id ->
                if (language.isNotEmpty()) remoteDataSource.writeNewUser(id, name, language)
            }
        }
    }
}