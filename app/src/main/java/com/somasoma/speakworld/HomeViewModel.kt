package com.somasoma.speakworld

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    val firebaseUser = FirebaseAuth.getInstance().currentUser
    private val _user = MutableLiveData(User("-", "-"))
    var user: LiveData<User> = _user

    private val remoteDataSource: RemoteDataSource = FirebaseRealtimeDB

    fun getUserInfo() {
        Timber.d(firebaseUser.toString())
        firebaseUser?.let {
            remoteDataSource.getUser(it.uid) { user -> _user.value = user }
        }
    }
}
