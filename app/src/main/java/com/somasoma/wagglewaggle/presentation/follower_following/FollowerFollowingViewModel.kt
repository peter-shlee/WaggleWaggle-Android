package com.somasoma.wagglewaggle.presentation.follower_following

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowerFollowingViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    val navigateToMainEvent = SingleLiveEvent<Unit>()
    val navigateToCreateWorldEvent = SingleLiveEvent<Unit>()
    val navigateToSettingEvent = SingleLiveEvent<Unit>()

    fun onClickCreateRoomButton() {
        navigateToCreateWorldEvent.call()
    }

    fun onClickHomeButton() {
        navigateToMainEvent.call()
    }

    fun onClickSettingButton() {
        navigateToSettingEvent.call()
    }
}