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
    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private val remoteDataSource: RemoteDataSource = FirebaseRealtimeDB
    var selectedAvatar: String? = null

    private val _user = MutableLiveData(User())
    private val _avatars = MutableLiveData(Avatars(arrayListOf()))
    private val _canPressStartButton = MutableLiveData(false)
    val user: LiveData<User> = _user
    val avatars: LiveData<Avatars> = _avatars
    val canPressStartButton: LiveData<Boolean> = _canPressStartButton

    init {
        remoteDataSource.getAvatars { characters ->
            _avatars.value = characters
        }
    }

    fun getUserInfo() {
        Timber.d(firebaseUser.toString())
        firebaseUser?.let {
            remoteDataSource.getUser(it.uid) { user -> _user.value = user }
        }
    }

    fun checkCanPressStartButton(): Boolean {
        if (firebaseUser == null) {
            _canPressStartButton.value = false
            return false
        }

        if (!isUserLoaded()) {
            _canPressStartButton.value = false
            return false
        }

        if (!isAvatarSelected()) {
            _canPressStartButton.value = false
            return false
        }

        _canPressStartButton.value = true
        return true
    }

    fun onClickStartButton() {
        if (!checkCanPressStartButton()) return
        setUserForActivity()
    }

    private fun setUserForActivity() {
        val userForUnity = UserForUnity

        userForUnity.id = firebaseUser?.uid
        userForUnity.name = user.value?.name
        userForUnity.country = user.value?.country
        userForUnity.avatar = selectedAvatar
    }

    private fun isUserLoaded(): Boolean {
        // TODO: 국가 선택 기능 추가하면 아래 주석 해제하여 국가 정보 있는지도 체크
        return !(user.value?.name.isNullOrBlank()/* || user.value?.country.isNullOrBlank()*/)
    }

    private fun isAvatarSelected(): Boolean {
        return !selectedAvatar.isNullOrBlank()
    }
}
