package com.somasoma.wagglewaggle.presentation.profile

import android.app.Application
import android.view.MenuItem
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.somasoma.wagglewaggle.data.Avatar
import com.somasoma.wagglewaggle.data.Friendship
import com.somasoma.wagglewaggle.data.model.dto.member.*
import com.somasoma.wagglewaggle.data.setDataForUnity
import com.somasoma.wagglewaggle.domain.usecase.member.*
import com.somasoma.wagglewaggle.presentation.*
import com.somasoma.wagglewaggle.presentation.custom_views.ProfileImageBackgroundColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    application: Application,
    private val sharedPreferenceHelper: SharedPreferenceHelper,
    private val networkUtil: NetworkUtil,
    private val getMemberUseCase: GetMemberUseCase,
    private val getFollowerUseCase: GetFollowerUseCase,
    private val getFollowingUseCase: GetFollowingUseCase,
    private val postFollowUseCase: PostFollowUseCase,
    private val deleteUnfollowUseCase: DeleteUnfollowUseCase,
    private val postBlockUseCase: PostBlockUseCase,
    private val deleteUnblockUseCase: DeleteUnblockUseCase
) :
    AndroidViewModel(application) {
    var member: Member = Member()
        set(value) {
            field = value
            _avatar.value = string2Avatar(value.avatar)
            _profileBackgroundColor.value = getProfileBackgroundColor(value.id ?: 0)
            _friendship.value = string2Friendship(value.friendship)
            value.interests?.let { _interests.value = it }
            getFollower(value.id ?: return)
            getFollowing(value.id)
        }
    private val _avatar = MutableStateFlow(DEFAULT_AVATAR)
    val avatar: StateFlow<Avatar> = _avatar
    private val _profileBackgroundColor = MutableStateFlow(ProfileImageBackgroundColor.YELLOW)
    val profileBackgroundColor: StateFlow<ProfileImageBackgroundColor> = _profileBackgroundColor
    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow: SharedFlow<Event> = _eventFlow
    private val _numOfFollowers = MutableStateFlow(0)
    val numOfFollowers: StateFlow<Int> = _numOfFollowers
    private val _numOfFollowings = MutableStateFlow(0)
    val numOfFollowings: StateFlow<Int> = _numOfFollowings
    private val _friendship = MutableStateFlow(Friendship.BLOCK)
    val friendship: StateFlow<Friendship> = _friendship
    private val _interests = MutableStateFlow<List<String?>>(listOf())
    val interests: StateFlow<List<String?>> = _interests
    private var currentMember: Member? = null

    init {
        getCurrentMember()
    }

    fun onClickBackButton() {
        event(Event.NavigateToPrevPage)
    }

    fun onClickFollowButton() {
        when(friendship.value) {
            Friendship.FOLLOW -> {
                deleteUnfollow()
            }
            Friendship.NONE -> {
                postFollow()
            }
            Friendship.BLOCK -> {

            }
        }
    }

    fun onClickEnterButton() {
        currentMember?.run {
            setDataForUnity(
                roomId = member.worldRoomInfo?.id,
                userId = id,
                avatar = avatar,
                language = language,
                country = country,
                world = member.worldRoomInfo?.map
            )
            event(Event.NavigateToUnityWorld)
        }
    }

    fun onClickMenu() {
        event(Event.OnMenuClicked)
    }

    fun onClickMenuItem(menuItem: MenuItem) {
        val id = member.id ?: return

        if (string2Friendship(member.friendship) == Friendship.BLOCK) {
            networkUtil.restApiCall(
                deleteUnblockUseCase::deleteUnblock,
                id,
                viewModelScope
            ) {
                onSuccessCallback = {
                    if (it?.status == UnblockResponse.OK) {
                        getMember(id)
                    }
                }

                onErrorCallback = {}

                onNetworkErrorCallback = {}
            }
        } else {
            networkUtil.restApiCall(
                postBlockUseCase::postBlock,
                id,
                viewModelScope
            ) {
                onSuccessCallback = {
                    if (it?.status == BlockResponse.OK) {
                        member = Member(
                            member.id,
                            member.nickName,
                            member.country,
                            member.language,
                            member.introduction,
                            member.avatar,
                            member.onlineStatus,
                            member.entranceStatus,
                            member.worldRoomInfo,
                            friendship2String(Friendship.BLOCK),
                            member.interests
                        )
                    }
                }

                onErrorCallback = {}

                onNetworkErrorCallback = {}
            }
        }
    }

    private fun postFollow() {
        networkUtil.restApiCall(
            postFollowUseCase::postFollow,
            member.id ?: return,
            viewModelScope
        ) {
            onSuccessCallback = {
                if (it?.status == FollowResponse.OK) {
                    _friendship.value = Friendship.FOLLOW
                    _numOfFollowers.value += 1
                }
            }
        }
    }

    private fun deleteUnfollow() {
        networkUtil.restApiCall(deleteUnfollowUseCase::deleteUnfollow, member.id?:return, viewModelScope) {
            onSuccessCallback = {
                if (it?.status == UnfollowResponse.OK) {
                    _friendship.value = Friendship.NONE
                    if (_numOfFollowers.value > 0) {
                        _numOfFollowers.value -= 1
                    }
                }
            }
        }
    }

    private fun getFollower(memberId: Int) {
        networkUtil.restApiCall(getFollowerUseCase::getFollower, memberId, viewModelScope) {
            onSuccessCallback = {
                it?.members?.run {
                    _numOfFollowers.value = size
                }
            }
        }
    }

    private fun getFollowing(memberId: Int) {
        networkUtil.restApiCall(getFollowingUseCase::getFollowing, memberId, viewModelScope) {
            onSuccessCallback = {
                it?.members?.run {
                    _numOfFollowings.value = size
                }
            }
        }
    }

    private fun getMember(memberId: Int) {
        networkUtil.restApiCall(getMemberUseCase::getMember, memberId, viewModelScope) {
            onSuccessCallback = {
                it?.let {
                    member = it
                }
            }

            onErrorCallback = {}

            onNetworkErrorCallback = {}
        }
    }

    private fun getCurrentMember() {
        networkUtil.restApiCall(getMemberUseCase::getMember, sharedPreferenceHelper.getInt(
            PreferenceConstant.MEMBER_ID), viewModelScope) {
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
        object NavigateToPrevPage : Event()
        object NavigateToUnityWorld: Event()
        object OnMenuClicked : Event()
    }
}