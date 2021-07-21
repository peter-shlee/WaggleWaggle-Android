package com.somasoma.speakworld

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingChangeNameViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    val firebaseUser = FirebaseAuth.getInstance().currentUser

    val remoteDataSource = FirebaseRealtimeDB

    var newName: String = ""

    fun saveNewUserName() {
        firebaseUser?.let { user ->
            if (newName != "") {
                remoteDataSource.setUserName(user.uid, newName)
            }
        }
    }
}