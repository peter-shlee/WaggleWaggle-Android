package com.somasoma.wagglewaggle.presentation.main.create_world

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.somasoma.wagglewaggle.core.InputState
import com.somasoma.wagglewaggle.core.NetworkUtil
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import com.somasoma.wagglewaggle.data.WorldMap
import com.somasoma.wagglewaggle.domain.usecase.member.GetInterestListUseCase
import com.somasoma.wagglewaggle.presentation.custom_views.SelectInterestsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class CreateWorldViewModel @Inject constructor(
    application: Application,
    private val networkUtil: NetworkUtil,
    getInterestListUseCase: GetInterestListUseCase
) : SelectInterestsViewModel(application, networkUtil, getInterestListUseCase) {
    companion object {
        private const val WORLD_NAME_REGEX = "[A-Za-z|가-힣|ㄱ-ㅎ|ㅏ-ㅣ\\d\\s]{1,20}\$"
        private val worldNamePattern = Pattern.compile(WORLD_NAME_REGEX)
    }

    val showSelectInterestsDialogEvent = SingleLiveEvent<Unit>()
    val navigatePrevPageEvent = SingleLiveEvent<Unit>()
    private val _maps = MutableLiveData<List<WorldMap>>()
    val maps: LiveData<List<WorldMap>> = _maps
    private val _worldNameInputState = MutableLiveData(InputState.ENABLED)
    val worldNameInputState: LiveData<InputState> = _worldNameInputState
    private var worldName: String = ""

    init {
        _maps.value = WorldMap.values().toList()
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
        navigatePrevPageEvent.call()
    }

    fun onClickSelectInterest() {
        showSelectInterestsDialogEvent.call()
    }

    fun onClickCreateButton() {

    }
}