package com.somasoma.wagglewaggle.presentation.follower_following

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowerFollowingViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow: SharedFlow<Event> = _eventFlow

    fun onClickCreateRoomButton() {
        event(Event.NavigateToCreateWorld)
    }

    fun onClickHomeButton() {
        event(Event.NavigateToMain)
    }

    fun onClickSettingButton() {
        event(Event.NavigateToSetting)
    }

    private fun event(event:Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        object NavigateToMain: Event()
        object NavigateToCreateWorld: Event()
        object NavigateToSetting: Event()
    }
}