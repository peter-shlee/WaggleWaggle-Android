package com.somasoma.wagglewaggle.domain.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import com.somasoma.wagglewaggle.core.model.Avatars
import com.somasoma.wagglewaggle.core.model.User
import com.somasoma.wagglewaggle.core.usecase.GetCharactersUseCase
import com.somasoma.wagglewaggle.core.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val getUserUseCase: GetUserUseCase,
    private val getCharactersUseCase: GetCharactersUseCase
) : AndroidViewModel(application) {
    private val _user = MutableLiveData(User("-", "-"))
    private val _characters = MutableLiveData(Avatars(arrayListOf()))
    val user: LiveData<User> = _user
    val avatars: LiveData<Avatars> = _characters
    val navigateToSettingActivityEvent = SingleLiveEvent<Unit>()
    var selectedCharacter: String? = null

    init {
        getCharactersUseCase.getCharacters { characters -> _characters.value = characters }
    }

    fun getUserInfo() {
        getUserUseCase.getUser { user -> _user.value = user }
    }

    fun onClickSettingButton() {
        navigateToSettingActivityEvent.call()
    }
}
