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
    private val _characters = MutableLiveData(Characters(arrayListOf()))
    val user: LiveData<User> = _user
    val characters: LiveData<Characters> = _characters
    private val remoteDataSource: RemoteDataSource = FirebaseRealtimeDB
    var selectedCharacter = "Basic"

    init {
        remoteDataSource.getCharacters { characters ->
            _characters.value = characters
            for (character in characters.list) {
                Timber.d(character)
            }
        }
    }

    fun getUserInfo() {
        Timber.d(firebaseUser.toString())
        firebaseUser?.let {
            remoteDataSource.getUser(it.uid) { user -> _user.value = user }
        }
    }
}
