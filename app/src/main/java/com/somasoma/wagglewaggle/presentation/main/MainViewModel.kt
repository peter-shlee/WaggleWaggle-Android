package com.somasoma.wagglewaggle.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.somasoma.wagglewaggle.core.NetworkUtil
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import com.somasoma.wagglewaggle.data.Avatar
import com.somasoma.wagglewaggle.data.model.dto.member.Member
import com.somasoma.wagglewaggle.data.model.dto.member.OnlineResponse
import com.somasoma.wagglewaggle.data.model.dto.world.WorldRoom
import com.somasoma.wagglewaggle.domain.usecase.member.GetOnlineUseCase
import com.somasoma.wagglewaggle.domain.usecase.world.GetWorldListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val networkUtil: NetworkUtil,
    private val getWorldListUseCase: GetWorldListUseCase,
    private val getOnlineUseCase: GetOnlineUseCase
) : AndroidViewModel(application) {
    var backgroundSemicircleRadius: Int = 0
    var backgroundSemicircleHeight: Int = 0
    var topBarHeight: Int = 0

    val navigateToSettingEvent = SingleLiveEvent<Unit>()
    val navigateToFollowerFollowing = SingleLiveEvent<Unit>()
    val navigateToCreateWorld = SingleLiveEvent<Unit>()
    val scrollToPrevAvatarEvent = SingleLiveEvent<Unit>()
    val scrollToNextAvatarEvent = SingleLiveEvent<Unit>()
    private val _avatars = MutableLiveData<List<Avatar>>()
    val avatars: LiveData<List<Avatar>> = _avatars
    private val _worlds = MutableLiveData<List<WorldRoom>>()
    val worlds: LiveData<List<WorldRoom>> = _worlds
    private val _onlineUsers = MutableLiveData<List<Member>>()
    val onlineUsers: LiveData<List<Member>> = _onlineUsers
    private val compositeDisposable = CompositeDisposable()

    init {
        makeAvatars()
        getOnlineUsers()
        getWorlds()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    private fun makeAvatars() {
        _avatars.value = Avatar.values().toList()
    }

    private fun getOnlineUsers() {
        networkUtil.restApiCall(getOnlineUseCase.getOnline(), compositeDisposable) {
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
        addNotFollowingOnlineMembersToList(onlineResponse, tmpOnlineUsers)

        return tmpOnlineUsers
    }

    private fun addNotFollowingOnlineMembersToList(
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
        networkUtil.restApiCall(getWorldListUseCase.getWorldList(), compositeDisposable) {
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