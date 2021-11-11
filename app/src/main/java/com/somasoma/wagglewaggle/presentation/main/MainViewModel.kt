package com.somasoma.wagglewaggle.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.somasoma.wagglewaggle.data.Avatar
import com.somasoma.wagglewaggle.data.model.dto.member.Member
import com.somasoma.wagglewaggle.data.model.dto.member.OnlineResponse
import com.somasoma.wagglewaggle.data.model.dto.world.WorldRoom
import com.somasoma.wagglewaggle.data.setDataForUnity
import com.somasoma.wagglewaggle.domain.usecase.member.GetMemberUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.GetOnlineUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.PutEditMemberUseCase
import com.somasoma.wagglewaggle.domain.usecase.world.GetWorldListUseCase
import com.somasoma.wagglewaggle.presentation.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val sharedPreferenceHelper: SharedPreferenceHelper,
    private val networkUtil: NetworkUtil,
    private val getMemberUseCase: GetMemberUseCase,
    private val getWorldListUseCase: GetWorldListUseCase,
    private val getOnlineUseCase: GetOnlineUseCase,
    private val putEditMemberUseCase: PutEditMemberUseCase
) : AndroidViewModel(application) {
    var backgroundSemicircleRadius: Int = 0
    var backgroundSemicircleHeight: Int = 0
    var topBarHeight: Int = 0

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow: SharedFlow<Event> = _eventFlow
    private val _avatars = MutableStateFlow<List<Avatar>>(listOf())
    val avatars: StateFlow<List<Avatar>> = _avatars
    private val _worlds = MutableStateFlow<List<WorldRoom>>(listOf())
    val worlds: StateFlow<List<WorldRoom>> = _worlds
    private val _onlineUsers = MutableStateFlow<List<Member>>(listOf())
    val onlineUsers: StateFlow<List<Member>> = _onlineUsers
    private var selectedAvatar: Avatar = DEFAULT_AVATAR
    private var currentMember: Member? = null
        set(value) {
            field = value
            value?.let {
                event(Event.MemberLoaded(it))
            }
        }
    var isAvatarSelectedByUser = false
    val onlineUserClickListener = object : MemberClickListener {
        override fun onClick(member: Member) {
            event(Event.NavigateToProfile(member))
        }
    }

    init {
        makeAvatars()
    }

    fun loadData() {
        getOnlineUsers()
        getWorlds()
        getMember()
    }

    private fun makeAvatars() {
        _avatars.value = Avatar.values().toList()
    }

    private fun getMember() {
        networkUtil.restApiCall(
            getMemberUseCase::getMember,
            sharedPreferenceHelper.getInt(PreferenceConstant.MEMBER_ID),
            viewModelScope
        ) {
            onSuccessCallback = {
                it?.let {
                    currentMember = it
                    selectedAvatar = string2Avatar(it.avatar)
                }
            }

            onErrorCallback = {}

            onNetworkErrorCallback = {}
        }
    }

    private fun getOnlineUsers() {
        networkUtil.restApiCall(getOnlineUseCase::getOnline, Unit, viewModelScope) {
            onSuccessCallback = { onlineResponse ->
                _onlineUsers.value = makeOnlineUserList(onlineResponse)
                Timber.d(onlineUsers.value.toString())
            }

            onErrorCallback = {

            }

            onNetworkErrorCallback = {

            }
        }
    }

    private fun makeOnlineUserList(onlineResponse: OnlineResponse?): MutableList<Member> {
        val tmpOnlineUsers = mutableListOf<Member>()

        addFollowingOnlineMembersToList(onlineResponse, tmpOnlineUsers)
        addOnlineMembersToList(onlineResponse, tmpOnlineUsers)

        return tmpOnlineUsers
    }

    private fun addOnlineMembersToList(
        onlineResponse: OnlineResponse?,
        tmpOnlineUsers: MutableList<Member>
    ) {
        onlineResponse?.onlineMembers?.let {
            addMembersToList(it, tmpOnlineUsers)
        }
    }

    private fun addFollowingOnlineMembersToList(
        onlineResponse: OnlineResponse?,
        tmpOnlineUsers: MutableList<Member>
    ) {
        onlineResponse?.onlineFollowingMembers?.let {
            addMembersToList(it, tmpOnlineUsers)
        }
    }

    private fun addMembersToList(
        members: List<Member?>,
        tmpOnlineUsers: MutableList<Member>
    ) {
        for (member in members) {
            member?.let {
                tmpOnlineUsers.add(member)
            }
        }
    }

    private fun getWorlds() {
        networkUtil.restApiCall(
            getWorldListUseCase::getWorldList,
            Unit,
            viewModelScope
        ) {
            onSuccessCallback = { it ->
                it?.worldRoomList?.let { worldRoomList ->
                    _worlds.value = makeWorlds(worldRoomList)
                    Timber.d(worlds.value.toString())
                }
            }

            onErrorCallback = {

            }

            onNetworkErrorCallback = {

            }
        }
    }

    private fun makeWorlds(worldRoomList: List<WorldRoom?>): MutableList<WorldRoom> {
        val tmpWorlds = mutableListOf<WorldRoom>()

        for (world in worldRoomList) {
            world?.let {
                tmpWorlds.add(world)
            }
        }
        return tmpWorlds
    }

    fun onAvatarSelected(index: Int) {
        selectedAvatar = avatars.value[index]

        if (!isAvatarSelectedByUser) return
        isAvatarSelectedByUser = false

        networkUtil.restApiCall(
            putEditMemberUseCase::putEditMember,
            Member(
                id = sharedPreferenceHelper.getInt(PreferenceConstant.MEMBER_ID),
                avatar = avatar2String(avatars.value[index])
            ),
            viewModelScope
        ) {
            onErrorCallback = {}

            onNetworkErrorCallback = {}
        }
    }

    fun onClickPrevAvatarButton() {
        isAvatarSelectedByUser = true
        event(Event.ScrollToPrevAvatar)
    }

    fun onClickNextAvatarButton() {
        isAvatarSelectedByUser = true
        event(Event.ScrollToNextAvatar)
    }

    fun onClickSettingButton() {
        event(Event.NavigateToSetting)
    }

    fun onClickFollowButton() {
        event(Event.NavigateToFollowerFollowing)
    }

    fun onClickCreateWorldButton() {
        event(Event.NavigateToCreateWorld)
    }

    fun onClickWorldEnterButton(worldRoom: WorldRoom) {
        setDataForUnity(
            roomId = worldRoom.id,
            userId = currentMember?.id,
            avatar = avatar2String(selectedAvatar),
            language = currentMember?.language,
            country = currentMember?.country,
            world = worldRoom.map
        )
        event(Event.NavigateToUnityWorld)
    }

    fun event(event:Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        object NavigateToSetting : Event()
        object NavigateToFollowerFollowing : Event()
        object NavigateToCreateWorld : Event()
        object NavigateToUnityWorld : Event()
        class NavigateToProfile(val member: Member) : Event()
        class MemberLoaded(val member: Member) : Event()
        object ScrollToPrevAvatar : Event()
        object ScrollToNextAvatar : Event()
    }
}