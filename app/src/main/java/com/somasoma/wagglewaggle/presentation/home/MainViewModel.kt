package com.somasoma.wagglewaggle.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import com.somasoma.wagglewaggle.data.Avatar
import com.somasoma.wagglewaggle.domain.usecase.member.GetOnlineUseCase
import com.somasoma.wagglewaggle.domain.usecase.world.GetWorldListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val getWorldListUseCase: GetWorldListUseCase,
    private val getOnlineUseCase: GetOnlineUseCase
) : AndroidViewModel(application) {
    var backgroundSemicircleRadius: Int = 0
    var backgroundSemicircleHeight: Int = 0

    val navigateToSettingEvent = SingleLiveEvent<Unit>()
    val navigateToFollowerFollowing = SingleLiveEvent<Unit>()
    val navigateToCreateWorld = SingleLiveEvent<Unit>()
    val scrollToPrevAvatarEvent = SingleLiveEvent<Unit>()
    val scrollToNextAvatarEvent = SingleLiveEvent<Unit>()
    private val _avatars = MutableLiveData<List<Avatar>>()
    val avatars: LiveData<List<Avatar>> = _avatars

    init {
        _avatars.value = Avatar.values().toList()
    }

    fun onAvatarSelected(index: Int) {
        val selectedAvatar = avatars.value?.get(index)
        Timber.d(selectedAvatar.toString())
    }

    fun onClickPrevAvatarButton() {
        scrollToPrevAvatarEvent.call()
    }

    fun onClickNextAvatarButton() {
        scrollToNextAvatarEvent.call()
    }

    fun onClickSettingButton() {
        navigateToSettingEvent.call()
    }

    fun onClickFollowButton() {
        navigateToFollowerFollowing.call()
    }

    fun onClickCreateWorldButton() {
        navigateToCreateWorld.call()
    }
}