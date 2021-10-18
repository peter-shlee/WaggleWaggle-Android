package com.somasoma.wagglewaggle.presentation.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.somasoma.wagglewaggle.core.DEFAULT_AVATAR
import com.somasoma.wagglewaggle.core.getProfileBackgroundColor
import com.somasoma.wagglewaggle.core.string2Avatar
import com.somasoma.wagglewaggle.data.Avatar
import com.somasoma.wagglewaggle.data.model.dto.member.Member
import com.somasoma.wagglewaggle.presentation.custom_views.ProfileImageBackgroundColor
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {
    var member: Member = Member()
        set(value) {
            field = value
            _avatar.value = string2Avatar(value.avatar)
            _profileBackgroundColor.value = getProfileBackgroundColor(value.id ?: 0)
        }
    private val _avatar = MutableStateFlow(DEFAULT_AVATAR)
    val avatar: StateFlow<Avatar> = _avatar
    private val _profileBackgroundColor = MutableStateFlow(ProfileImageBackgroundColor.YELLOW)
    val profileBackgroundColor: StateFlow<ProfileImageBackgroundColor> = _profileBackgroundColor
    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow: SharedFlow<Event> = _eventFlow

    fun onClickBackButton() {
        event(Event.NavigateToPrevPage)
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        object NavigateToPrevPage : Event()
    }
}