package com.somasoma.wagglewaggle.presentation.main.create_world

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.somasoma.wagglewaggle.data.WorldMap
import com.somasoma.wagglewaggle.data.model.dto.member.Member
import com.somasoma.wagglewaggle.data.model.dto.world.WorldRoom
import com.somasoma.wagglewaggle.data.setDataForUnity
import com.somasoma.wagglewaggle.domain.usecase.member.GetInterestListUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.GetMemberUseCase
import com.somasoma.wagglewaggle.domain.usecase.world.PostNewWorldUseCase
import com.somasoma.wagglewaggle.presentation.*
import com.somasoma.wagglewaggle.presentation.custom_views.SelectInterestsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class CreateWorldViewModel @Inject constructor(
    application: Application,
    private val sharedPreferenceHelper: SharedPreferenceHelper,
    private val networkUtil: NetworkUtil,
    getInterestListUseCase: GetInterestListUseCase,
    private val postNewWorldUseCase: PostNewWorldUseCase,
    private val getMemberUseCase: GetMemberUseCase
) : SelectInterestsViewModel(application, networkUtil, getInterestListUseCase) {
    companion object {
        private const val WORLD_NAME_REGEX = "[A-Za-z|가-힣|ㄱ-ㅎ|ㅏ-ㅣ\\d\\s]{1,20}\$"
        private val worldNamePattern = Pattern.compile(WORLD_NAME_REGEX)
    }

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow: SharedFlow<Event> = _eventFlow
    private val _maps = MutableStateFlow<List<WorldMap>>(listOf())
    val maps: StateFlow<List<WorldMap>> = _maps
    private val _worldNameInputState = MutableStateFlow(InputState.ENABLED)
    val worldNameInputState: StateFlow<InputState> = _worldNameInputState
    private var worldName: String = ""
    private var currentMember: Member? = null

    init {
        getMember()
        _maps.value = listOf(WorldMap.JONGMYO)
//        _maps.value = WorldMap.values().toList()
    }

    fun onWorldNameChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _worldNameInputState.value = InputState.ENABLED
        worldName = s.toString().trim()
        validateWorldName(worldName)
    }

    private fun validateWorldName(worldName: String) {
        if (worldName.isBlank()) {
            _worldNameInputState.value = InputState.ENABLED
            return
        }

        if (worldNamePattern.matcher(worldName).matches()) {
            _worldNameInputState.value = InputState.POSITIVE
        } else {
            _worldNameInputState.value = InputState.NEGATIVE
        }
    }

    fun onClickBackButton() {
        event(Event.NavigateToPrevPage)
    }

    fun onClickSelectInterest() {
        event(Event.ShowSelectInterestsDialog)
    }

    fun onClickCreateButton() {
        if (currentMember == null) return

        networkUtil.restApiCall(
            postNewWorldUseCase::postNewWorld, WorldRoom(
                null,
                worldName,
                null,
                null,
                null,
                null,
                selectedInterests.value.toList(),
                null
            ), viewModelScope
        ) {
            onSuccessCallback = {
                it?.run {
                    setDataForUnity(
                        roomId = id,
                        userId = currentMember?.id,
                        avatar = string2Avatar(currentMember?.avatar),
                        language = currentMember?.language,
                        country = currentMember?.country,
                        world = worldMap2String(WorldMap.JONGMYO)
                    )
                    event(Event.NavigateToUnityWorld)
                }
            }
        }
    }

    private fun getMember(){
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

    fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event{
        object NavigateToPrevPage: Event()
        object NavigateToUnityWorld: Event()
        object ShowSelectInterestsDialog: Event()
    }
}