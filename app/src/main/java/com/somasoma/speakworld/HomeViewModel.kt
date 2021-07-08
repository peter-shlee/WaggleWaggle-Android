package com.somasoma.speakworld

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(application: Application): AndroidViewModel(application) {
    var firebaseUser: FirebaseUser? = null
    private var _user = MutableLiveData(User("-", "-"))
    var user = _user

    private val remoteDataSource: RemoteDataSource = FirebaseRealtimeDB

    fun getUserInfo(){
        firebaseUser?.let {
            remoteDataSource.getUser(it.uid) {user -> _user.value = user}
        }
    }

}