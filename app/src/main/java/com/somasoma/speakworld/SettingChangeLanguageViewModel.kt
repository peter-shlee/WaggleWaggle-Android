package com.somasoma.speakworld

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingChangeLanguageViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    val firebaseUser = FirebaseAuth.getInstance().currentUser

    val remoteDataSource = FirebaseRealtimeDB

    var newLanguage: String = ""

    fun saveNewUserLanguage() {
        firebaseUser?.let { user ->
            if (newLanguage != "") {
                remoteDataSource.setUserLanguage(firebaseUser.uid, newLanguage)
            }
        }
    }
}