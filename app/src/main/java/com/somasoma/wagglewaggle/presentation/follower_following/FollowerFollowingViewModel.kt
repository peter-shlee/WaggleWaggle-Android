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

    fun onCreateRoomButtonClicked() {
        navigateToCreateWorldEvent.call()
    }

    fun onHomeButtonClicked() {
        navigateToMainEvent.call()
    }
}