package com.somasoma.speakworld

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    val remoteDataSource = FirebaseRealtimeDB

    val firebaseUser = FirebaseAuth.getInstance().currentUser

}