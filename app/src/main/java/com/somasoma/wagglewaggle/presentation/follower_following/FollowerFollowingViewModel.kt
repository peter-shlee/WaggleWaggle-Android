package com.somasoma.wagglewaggle.presentation.follower_following

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.somasoma.wagglewaggle.core.NetworkUtil
import com.somasoma.wagglewaggle.core.PreferenceConstant
import com.somasoma.wagglewaggle.core.SharedPreferenceHelper
import com.somasoma.wagglewaggle.domain.usecase.member.GetFollowerUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.GetFollowingUseCase
import com.somasoma.wagglewaggle.data.model.dto.member.Member
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowerFollowingViewModel @Inject constructor(
    application: Application,
    private val networkUtil: NetworkUtil,
    private val sharedPreferenceHelper: SharedPreferenceHelper,
    private val getFollowerUseCase: GetFollowerUseCase,
    private val getFollowingUseCase: GetFollowingUseCase
) :
    AndroidViewModel(application) {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow: SharedFlow<Event> = _eventFlow
    private val _following = MutableStateFlow<List<Member>>(listOf())
    val following: StateFlow<List<Member>> = _following
    private val _follower = MutableStateFlow<List<Member>>(listOf())
    val follower: StateFlow<List<Member>> = _follower

    init {
        getFollower()
        getFollowing()
    }

    fun onClickCreateRoomButton() {
        event(Event.NavigateToCreateWorld)
    }

    fun onClickHomeButton() {
        event(Event.NavigateToMain)
    }

    fun onClickSettingButton() {
        event(Event.NavigateToSetting)
    }

    private fun getFollower() {
        networkUtil.restApiCall(
            getFollowerUseCase::getFollower,
            sharedPreferenceHelper.getInt(PreferenceConstant.MEMBER_ID),
            viewModelScope
        ) {
            onSuccessCallback = {
                val tmpFollower = mutableListOf<Member>()
                it?.members?.let { members ->
                    for (member in members) {
                        member?.let {
                            tmpFollower.add(member)
                        }
                    }
                }
                _following.value = tmpFollower
            }
        }
    }

    private fun getFollowing() {
        networkUtil.restApiCall(
            getFollowingUseCase::getFollowing,
            sharedPreferenceHelper.getInt(PreferenceConstant.MEMBER_ID),
            viewModelScope
        ) {
            onSuccessCallback = {
                val tmpFollowing = mutableListOf<Member>()
                it?.members?.let { members ->
                    for (member in members) {
                        member?.let {
                            tmpFollowing.add(member)
                        }
                    }
                }
                _following.value = tmpFollowing
            }
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        object NavigateToMain : Event()
        object NavigateToCreateWorld : Event()
        object NavigateToSetting : Event()
    }
}