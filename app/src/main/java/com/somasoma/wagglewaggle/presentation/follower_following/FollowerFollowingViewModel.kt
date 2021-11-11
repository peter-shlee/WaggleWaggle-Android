package com.somasoma.wagglewaggle.presentation.follower_following

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.somasoma.wagglewaggle.data.model.dto.member.Member
import com.somasoma.wagglewaggle.data.model.dto.world.WorldRoom
import com.somasoma.wagglewaggle.data.setDataForUnity
import com.somasoma.wagglewaggle.domain.usecase.member.GetFollowerUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.GetFollowingUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.GetMemberUseCase
import com.somasoma.wagglewaggle.presentation.NetworkUtil
import com.somasoma.wagglewaggle.presentation.PreferenceConstant
import com.somasoma.wagglewaggle.presentation.SharedPreferenceHelper
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
    private val getFollowingUseCase: GetFollowingUseCase,
    private val getMemberUseCase: GetMemberUseCase
) :
    AndroidViewModel(application) {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow: SharedFlow<Event> = _eventFlow
    private val _following = MutableStateFlow<List<Member>>(listOf())
    val following: StateFlow<List<Member>> = _following
    private val _follower = MutableStateFlow<List<Member>>(listOf())
    val follower: StateFlow<List<Member>> = _follower
    private val _showFollower = MutableStateFlow(true)
    val showFollower: StateFlow<Boolean> = _showFollower
    private var currentMember: Member? = null

    init {
        getFollower()
        getFollowing()
        getCurrentMember()
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

    fun onFollowerTabClicked() {
        _showFollower.value = true
    }

    fun onFollowingTabClicked() {
        _showFollower.value = false
    }

    fun onFollowMemberClicked(member: Member) {
        event(Event.NavigateToProfile(member))
    }

    fun onEnterButtonClicked(worldRoom: WorldRoom) {
        currentMember?.run {
            setDataForUnity(
                roomId = worldRoom.id,
                userId = id,
                avatar = avatar,
                language = language,
                country = country,
                world = worldRoom.map
            )
            event(Event.NavigateToUnityWorld)
        }
    }

    fun getFollower() {
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
                _follower.value = tmpFollower
            }
        }
    }

    fun getFollowing() {
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

    private fun getCurrentMember() {
        networkUtil.restApiCall(
            getMemberUseCase::getMember,
            sharedPreferenceHelper.getInt(PreferenceConstant.MEMBER_ID),
            viewModelScope,
        ) {
            onSuccessCallback = {
                currentMember = it
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
        object NavigateToUnityWorld : Event()
        class NavigateToProfile(val member: Member): Event()
    }
}